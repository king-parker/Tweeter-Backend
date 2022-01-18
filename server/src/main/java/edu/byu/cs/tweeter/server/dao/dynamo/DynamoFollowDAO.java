package edu.byu.cs.tweeter.server.dao.dynamo;

import com.amazonaws.services.dynamodbv2.document.DeleteItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.PutItemSpec;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.QueryRequest;
import com.amazonaws.services.dynamodbv2.model.QueryResult;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.DataAccessException;
import edu.byu.cs.tweeter.server.dao.FollowDAO;
import edu.byu.cs.tweeter.server.service.Service;
import edu.byu.cs.tweeter.server.util.Pair;

import java.util.*;

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
        Pair<List<User>, Boolean> result = new Pair<>(null, null);

        String fllr = "#fllr";
        Map<String, String> attrNames = new HashMap<>();
        attrNames.put(fllr, PARTITION_KEY);

        String fllrValue = ":follower";
        Map<String, AttributeValue> attrValues = new HashMap<>();
        attrValues.put(fllrValue, new AttributeValue().withS(followerAlias));

        QueryRequest queryRequest = new QueryRequest()
                .withTableName(TABLE_NAME)
                .withKeyConditionExpression(fllr + " = " + fllrValue)
                .withExpressionAttributeNames(attrNames)
                .withExpressionAttributeValues(attrValues)
                .withLimit(limit);

        if (isNonEmptyString(lastFolloweeAlias)) {
            Map<String, AttributeValue> startKey = new HashMap<>();
            startKey.put(PARTITION_KEY, new AttributeValue().withS(followerAlias));
            startKey.put(SORT_KEY, new AttributeValue().withS(lastFolloweeAlias));

            queryRequest = queryRequest.withExclusiveStartKey(startKey);
        }

        QueryResult queryResult = DynamoDAOFactory.getDbClient().query(queryRequest);
        List<Map<String, AttributeValue>> items = queryResult.getItems();
        if (items != null) {
            result.setFirst(new ArrayList<>());
            for (Map<String, AttributeValue> item : items){
                String firstname = item.get(ATT_FWE_FN_KEY).getS();
                String lastname = item.get(ATT_FWE_LN_KEY).getS();
                String alias = item.get(SORT_KEY).getS();
                String image = item.get(ATT_FWE_IMURL_NAME).getS();
                result.getFirst().add(new User(firstname, lastname, alias, image));
            }
        }

        Map<String, AttributeValue> lastKey = queryResult.getLastEvaluatedKey();
        result.setSecond(lastKey != null);

        return result;
    }

    @Override
    public int getFollowerCount(String followeeAlias) {
        return new DynamoUserDAO().getFollowerCount(followeeAlias);
    }

    @Override
    public Pair<List<User>, Boolean> getFollowers(String followeeAlias, int limit, String lastFollowerAlias) {
        Pair<List<User>, Boolean> result = new Pair<>(null, null);

        String flle = "#flle";
        Map<String, String> attrNames = new HashMap<>();
        attrNames.put(flle, INDEX_PARTITION_KEY);

        String flleValue = ":followee";
        Map<String, AttributeValue> attrValues = new HashMap<>();
        attrValues.put(flleValue, new AttributeValue().withS(followeeAlias));

        QueryRequest queryRequest = new QueryRequest()
                .withTableName(TABLE_NAME)
                .withIndexName(INDEX_NAME)
                .withKeyConditionExpression(flle + " = " + flleValue)
                .withExpressionAttributeNames(attrNames)
                .withExpressionAttributeValues(attrValues)
                .withLimit(limit);

        if (isNonEmptyString(lastFollowerAlias)) {
            Map<String, AttributeValue> startKey = new HashMap<>();
            startKey.put(INDEX_PARTITION_KEY, new AttributeValue().withS(followeeAlias));
            startKey.put(INDEX_SORT_KEY, new AttributeValue().withS(lastFollowerAlias));

            queryRequest = queryRequest.withExclusiveStartKey(startKey);
        }

        QueryResult queryResult = DynamoDAOFactory.getDbClient().query(queryRequest);
        List<Map<String, AttributeValue>> items = queryResult.getItems();
        if (items != null) {
            result.setFirst(new ArrayList<>());
            for (Map<String, AttributeValue> item : items){
                String firstname = item.get(ATT_FWR_FN_KEY).getS();
                String lastname = item.get(ATT_FWR_LN_KEY).getS();
                String alias = item.get(INDEX_SORT_KEY).getS();
                String image = item.get(ATT_FWR_IMURL_NAME).getS();
                result.getFirst().add(new User(firstname, lastname, alias, image));
            }
        }

        Map<String, AttributeValue> lastKey = queryResult.getLastEvaluatedKey();
        result.setSecond(lastKey != null);

        return result;
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

    private static boolean isNonEmptyString(String value) {
        return (value != null && value.length() > 0);
    }
}
