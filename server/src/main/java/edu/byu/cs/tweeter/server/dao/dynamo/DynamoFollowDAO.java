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
        GetItemSpec spec = new GetItemSpec().withPrimaryKey(PARTITION_KEY, followerAlias, SORT_KEY, followeeAlias);

        Item outcome = table.getItem(spec);

        return outcome != null;
    }

    @Override
    public void follow(String followerAlias, String followeeAlias) {
        if (isFollower(followerAlias, followeeAlias))
            throw new DataAccessException(Service.BAD_REQUEST_TAG + " Follow relationship already exists");

        Pair<User, User> userPair = checkForUsers(followerAlias, followeeAlias);
        User follower = userPair.getFirst();
        User followee = userPair.getSecond();

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
            System.out.println(e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
            throw new DataAccessException(Service.SERVER_ERROR_TAG + " Error creating user", e.getCause());
        }
    }

    @Override
    public void unfollow(String followerAlias, String followeeAlias) {
        if (!isFollower(followerAlias, followeeAlias))
            throw new DataAccessException(Service.BAD_REQUEST_TAG + " Follow relationship does not exists");

        checkForUsers(followerAlias, followeeAlias);

        DynamoUserDAO userDAO = new DynamoUserDAO();
        userDAO.changeFolloweeCount(followerAlias, false);
        userDAO.changeFollowCount(followeeAlias, false);

        DeleteItemSpec spec = new DeleteItemSpec().withPrimaryKey(PARTITION_KEY, followerAlias, SORT_KEY, followeeAlias);

        try {
            DeleteItemOutcome outcome = table.deleteItem(spec);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
            throw new DataAccessException(Service.SERVER_ERROR_TAG + " Error creating user", e.getCause());
        }
    }

    @Override
    public int getFolloweeCount(String followerAlias) {
        return new DynamoUserDAO().getFolloweeCount(followerAlias);
    }

    @Override
    public Pair<List<User>, Boolean> getFollowees(String followerAlias, int limit, String lastFolloweeAlias) {

        QueryResult queryResult = PaginatedRequestStrategy.makeQuery(TABLE_NAME, PARTITION_KEY, followerAlias,
                SORT_KEY, lastFolloweeAlias, limit, null);

        return extractUserResults(queryResult, ATT_FWE_FN_KEY, ATT_FWE_LN_KEY, SORT_KEY, ATT_FWE_IMURL_NAME);
    }

    @Override
    public int getFollowerCount(String followeeAlias) {
        return new DynamoUserDAO().getFollowerCount(followeeAlias);
    }

    @Override
    public Pair<List<User>, Boolean> getFollowers(String followeeAlias, int limit, String lastFollowerAlias) {
        QueryResult queryResult = PaginatedRequestStrategy.makeQuery(TABLE_NAME, INDEX_PARTITION_KEY, followeeAlias,
                INDEX_SORT_KEY, lastFollowerAlias, limit, INDEX_NAME);

        return extractUserResults(queryResult, ATT_FWR_FN_KEY, ATT_FWR_LN_KEY, INDEX_SORT_KEY, ATT_FWR_IMURL_NAME);
    }

    private Pair<User, User> checkForUsers(String followerAlias, String followeeAlias) {
        DynamoUserDAO userDAO = new DynamoUserDAO();
        Pair<User, User> users = new Pair<>(null, null);

        try {
            users.setFirst(userDAO.getUser(followerAlias));
        } catch (Exception e) {
            String error = "Could not retrieve " + followerAlias;
            System.out.println(error);
            throw new DataAccessException(Service.SERVER_ERROR_TAG + " " + error);
        }

        try {
            users.setSecond(userDAO.getUser(followeeAlias));
        } catch (Exception e) {
            String error = "Could not retrieve " + followeeAlias;
            System.out.println(error);
            throw new DataAccessException(Service.SERVER_ERROR_TAG + " " + error);
        }

        return users;
    }

    private Pair<List<User>, Boolean> extractUserResults(QueryResult queryResult, String firstnameKey, String lastnameKey, String aliasKey, String imageKey) {
        return PaginatedRequestStrategy.parseQueryResult(queryResult, item -> {
            String firstname = item.get(firstnameKey).getS();
            String lastname = item.get(lastnameKey).getS();
            String alias = item.get(aliasKey).getS();
            String image = item.get(imageKey).getS();
            return new User(firstname, lastname, alias, image);
        });
    }
}
