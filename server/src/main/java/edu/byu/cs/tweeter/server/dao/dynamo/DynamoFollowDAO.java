package edu.byu.cs.tweeter.server.dao.dynamo;

import com.amazonaws.services.dynamodbv2.document.DeleteItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.PutItemSpec;
import com.amazonaws.services.dynamodbv2.model.QueryResult;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.DataAccessException;
import edu.byu.cs.tweeter.server.dao.FollowDAO;
import edu.byu.cs.tweeter.server.service.Service;
import edu.byu.cs.tweeter.server.util.Pair;

import java.util.Arrays;
import java.util.List;

public class DynamoFollowDAO implements FollowDAO {
    private final String LOG_TAG = "DYNAMO_FOLLOW_DAO";

    private final String TABLE_NAME = "tweeter-follows";
    private final String INDEX_NAME = "followee_alias-follower_alias-index";
    private final String PARTITION_KEY = "follower_alias";
    private final String SORT_KEY = "followee_alias";
    private final String INDEX_PARTITION_KEY = SORT_KEY;
    private final String INDEX_SORT_KEY = PARTITION_KEY;
    private final String ATT_FWR_FN_KEY = "follower_first_name";
    private final String ATT_FWR_LN_KEY = "follower_last_name";
    private final String ATT_FWR_IMURL_NAME = "follower_image_url";
    private final String ATT_FWE_FN_KEY = "followee_first_name";
    private final String ATT_FWE_LN_KEY = "followee_last_name";
    private final String ATT_FWE_IMURL_NAME = "followee_image_url";

    private final Table table;

    public DynamoFollowDAO() {
        table = DynamoDAOFactory.getDatabase().getTable(TABLE_NAME);
    }

    @Override
    public boolean isFollower(String followerAlias, String followeeAlias) {
        System.out.printf("%s: Checking is %s follows %s%n", LOG_TAG, followerAlias, followeeAlias);
        GetItemSpec spec = new GetItemSpec().withPrimaryKey(PARTITION_KEY, followerAlias, SORT_KEY, followeeAlias);

        try {
            Item outcome = table.getItem(spec);
            return outcome != null;
        }
        catch (Exception e) {
            String error = "Could not check follow relationship";
            System.out.printf("%s: %s%n", LOG_TAG, error);
            throw new DataAccessException(Service.SERVER_ERROR_TAG + " " + error);
        }
    }

    @Override
    public void follow(String followerAlias, String followeeAlias) {
        System.out.printf("%s: Attempting to have %s follow %s%n", LOG_TAG, followerAlias, followeeAlias);
        if (isFollower(followerAlias, followeeAlias)) {
            String error = "Follow relationship already exists";
            System.out.printf("%s: %s%n", LOG_TAG, error);
            throw new DataAccessException(Service.BAD_REQUEST_TAG + " " + error);
        }

        System.out.printf("%s: Checking to see if both users are in the user table%n", LOG_TAG);
        Pair<User, User> userPair = checkForUsers(followerAlias, followeeAlias);
        User follower = userPair.getFirst();
        User followee = userPair.getSecond();

        System.out.printf("%s: Changing the follow and following counts for the users%n", LOG_TAG);
        DynamoUserDAO userDAO = new DynamoUserDAO();
        userDAO.changeFolloweeCount(followerAlias, true);
        userDAO.changeFollowCount(followeeAlias, true);

        Item item = new Item().withPrimaryKey(PARTITION_KEY, followerAlias, SORT_KEY, followeeAlias)
                .withString(ATT_FWR_FN_KEY, follower.getFirstName())
                .withString(ATT_FWR_LN_KEY, follower.getLastName())
                .withString(ATT_FWR_IMURL_NAME, follower.getImageUrl())
                .withString(ATT_FWE_FN_KEY, followee.getFirstName())
                .withString(ATT_FWE_LN_KEY, followee.getLastName())
                .withString(ATT_FWE_IMURL_NAME, followee.getImageUrl());
        PutItemSpec spec = new PutItemSpec().withItem(item);

        try {
            PutItemOutcome outcome = table.putItem(spec);
        } catch (Exception e) {
            System.out.printf("%s: %s%n", LOG_TAG, e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
            throw new DataAccessException(Service.SERVER_ERROR_TAG + " Error creating follow relationship", e.getCause());
        }
    }

    @Override
    public void unfollow(String followerAlias, String followeeAlias) {
        System.out.printf("%s: Attempting to have %s unfollow %s%n", LOG_TAG, followerAlias, followeeAlias);
        if (!isFollower(followerAlias, followeeAlias)) {
            String error = "Follow relationship does not exists";
            System.out.printf("%s: %s%n", LOG_TAG, error);
            throw new DataAccessException(Service.BAD_REQUEST_TAG + " " + error);
        }

        System.out.printf("%s: Checking to see if both users are in the user table%n", LOG_TAG);
        checkForUsers(followerAlias, followeeAlias);

        System.out.printf("%s: Changing the follow and following counts for the users%n", LOG_TAG);
        DynamoUserDAO userDAO = new DynamoUserDAO();
        userDAO.changeFolloweeCount(followerAlias, false);
        userDAO.changeFollowCount(followeeAlias, false);

        DeleteItemSpec spec = new DeleteItemSpec().withPrimaryKey(PARTITION_KEY, followerAlias, SORT_KEY, followeeAlias);

        try {
            DeleteItemOutcome outcome = table.deleteItem(spec);
        } catch (Exception e) {
            System.out.printf("%s: %s%n", LOG_TAG, e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
            throw new DataAccessException(Service.SERVER_ERROR_TAG + " Error removing follow relationship", e.getCause());
        }
    }

    @Override
    public int getFolloweeCount(String followerAlias) {
        System.out.printf("%s: Getting followee count from user table%n", LOG_TAG);
        return new DynamoUserDAO().getFolloweeCount(followerAlias);
    }

    @Override
    public Pair<List<User>, Boolean> getFollowees(String followerAlias, int limit, String lastFolloweeAlias) {
        System.out.printf("%s: Getting a page of %d followees for %s%n", LOG_TAG, limit, followerAlias);

        QueryResult queryResult = PaginatedRequestStrategy.makeQuery(TABLE_NAME, PARTITION_KEY, followerAlias,
                SORT_KEY, lastFolloweeAlias, limit, null);

        return PaginatedRequestStrategy.UserExtractor.extractResults(queryResult, ATT_FWE_FN_KEY, ATT_FWE_LN_KEY, SORT_KEY, ATT_FWE_IMURL_NAME);
    }

    @Override
    public int getFollowerCount(String followeeAlias) {
        System.out.printf("%s: Getting follower count from user table%n", LOG_TAG);
        return new DynamoUserDAO().getFollowerCount(followeeAlias);
    }

    @Override
    public Pair<List<User>, Boolean> getFollowers(String followeeAlias, int limit, String lastFollowerAlias) {
        System.out.printf("%s: Getting a page of %d followers for %s%n", LOG_TAG, limit, followeeAlias);

        QueryResult queryResult = PaginatedRequestStrategy.makeQuery(TABLE_NAME, INDEX_PARTITION_KEY, followeeAlias,
                INDEX_SORT_KEY, lastFollowerAlias, limit, INDEX_NAME);

        return PaginatedRequestStrategy.UserExtractor.extractResults(queryResult, ATT_FWR_FN_KEY, ATT_FWR_LN_KEY, INDEX_SORT_KEY, ATT_FWR_IMURL_NAME);
    }

    private Pair<User, User> checkForUsers(String followerAlias, String followeeAlias) {
        DynamoUserDAO userDAO = new DynamoUserDAO();
        Pair<User, User> users = new Pair<>(null, null);

        try {
            users.setFirst(userDAO.getUser(followerAlias));
        } catch (Exception e) {
            String error = "Could not retrieve " + followerAlias;
            System.out.printf("%s: %s%n", LOG_TAG, error);
            throw new DataAccessException(Service.SERVER_ERROR_TAG + " " + error);
        }

        try {
            users.setSecond(userDAO.getUser(followeeAlias));
        } catch (Exception e) {
            String error = "Could not retrieve " + followeeAlias;
            System.out.printf("%s: %s%n", LOG_TAG, error);
            throw new DataAccessException(Service.SERVER_ERROR_TAG + " " + error);
        }

        return users;
    }
}
