package edu.byu.cs.tweeter.server.dao.dynamo;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.UpdateItemOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.PutItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.DataAccessException;
import edu.byu.cs.tweeter.server.dao.UserDAO;
import edu.byu.cs.tweeter.server.service.Service;

import java.util.Arrays;

public class DynamoUserDAO implements UserDAO {
    private final String TABLE_NAME = "tweeter-user";
    private final String PARTITION_KEY = "user_alias";
    private final String ATT_FN_NAME = "first_name";
    private final String ATT_LN_NAME = "last_name";
    private final String ATT_PASS_NAME = "password";
    private final String ATT_IMURL_NAME = "image_url";
    private final String ATT_FWC_NAME = "follow_count";
    private final String ATT_FEC_NAME = "followee_count";

    private final Table table;

    public DynamoUserDAO() {
        table = DynamoDAOFactory.getDatabase().getTable(TABLE_NAME);
    }

    @Override
    public User getUser(String alias) {
        GetItemSpec spec = new GetItemSpec().withPrimaryKey(PARTITION_KEY, alias);
        User user;

        Item outcome = table.getItem(spec);

        if (outcome != null) {
            user = new User(outcome.getString(ATT_FN_NAME), outcome.getString(ATT_LN_NAME), outcome.getString(PARTITION_KEY), outcome.getString(ATT_IMURL_NAME));
        }
        else {
            String error = "Could not retrieve user";
            System.out.println(error);
            throw new DataAccessException(Service.SERVER_ERROR_TAG + " " + error);
        }

        return user;
    }

    @Override
    public User getLoginUser(String username, String password) {
        GetItemSpec spec = new GetItemSpec().withPrimaryKey(PARTITION_KEY, username);
        User user;

        Item outcome = table.getItem(spec);

        if (outcome != null) {
            user = new User(outcome.getString(ATT_FN_NAME), outcome.getString(ATT_LN_NAME), outcome.getString(PARTITION_KEY), outcome.getString(ATT_IMURL_NAME));
        }
        else {
            String error = "Could not retrieve user";
            System.out.println(error);
            throw new DataAccessException(Service.SERVER_ERROR_TAG + " " + error);
        }

        if (outcome.getString(ATT_PASS_NAME).equals(password)) return user;
        else return null;
    }

    @Override
    public User registerNewUser(String firstName, String lastName, String username, String password, String imageBytesBase64) {

        try {
            getUser(username);
            return null;
        } catch (DataAccessException e) {
            // Username available to be used, do nothing
        }

        String imageUrl = new S3DAO().upload(username, imageBytesBase64);

        Item item = new Item().withPrimaryKey(PARTITION_KEY, username)
                .withString(ATT_FN_NAME, firstName)
                .withString(ATT_LN_NAME, lastName)
                .withString(ATT_PASS_NAME, password)
                .withString(ATT_IMURL_NAME, imageUrl)
                .withInt(ATT_FWC_NAME, 0)
                .withInt(ATT_FEC_NAME, 0);
        PutItemSpec spec = new PutItemSpec().withItem(item);//.withConditionExpression("attribute_not_exists(" + PARTITION_KEY + ")");

        try {
            PutItemOutcome outcome = table.putItem(spec);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
            throw new DataAccessException(Service.SERVER_ERROR_TAG + " Error creating user", e.getCause());
        }



        return new User(firstName, lastName, username, imageUrl);
    }

    public int getFollowCount(String alias) {
        GetItemSpec spec = new GetItemSpec().withPrimaryKey(PARTITION_KEY, alias);

        Item outcome = table.getItem(spec);

        if (outcome != null) {
            return outcome.getInt(ATT_FWC_NAME);
        }
        else {
            String error = "Could not retrieve user to get follow count";
            System.out.println(error);
            throw new DataAccessException(Service.SERVER_ERROR_TAG + " " + error);
        }
    }

    public int getFolloweeCount(String alias) {
        GetItemSpec spec = new GetItemSpec().withPrimaryKey(PARTITION_KEY, alias);

        Item outcome = table.getItem(spec);

        if (outcome != null) {
            return outcome.getInt(ATT_FEC_NAME);
        }
        else {
            String error = "Could not retrieve user to get followee count";
            System.out.println(error);
            throw new DataAccessException(Service.SERVER_ERROR_TAG + " " + error);
        }
    }

    public void changeFollowCount(String alias, boolean newFollow) {
        String inc = ":inc";
        int increment;

        if (newFollow) {
            increment = 1;
        }
        else {
            increment = -1;
        }
        UpdateItemSpec spec = new UpdateItemSpec().withPrimaryKey(PARTITION_KEY, alias)
                .withUpdateExpression("set " + ATT_FWC_NAME + "=" + ATT_FWC_NAME + " + " + inc)
                .withValueMap(new ValueMap().withInt(inc, increment))
                .withConditionExpression("attribute_exists(" + PARTITION_KEY + ")");

        try {
            UpdateItemOutcome outcome = table.updateItem(spec);
        } catch (Exception e) {
            String error = "Could not update user count";
            System.out.println(error);
            throw new DataAccessException(Service.SERVER_ERROR_TAG + " " + error);
        }
    }

    public void changeFolloweeCount(String alias, boolean newFollowee) {
        String inc = ":inc";
        int increment;

        if (newFollowee) {
            increment = 1;
        }
        else {
            increment = -1;
        }
        UpdateItemSpec spec = new UpdateItemSpec().withPrimaryKey(PARTITION_KEY, alias)
                .withUpdateExpression("set " + ATT_FEC_NAME + "=" + ATT_FEC_NAME + " + " + inc)
                .withValueMap(new ValueMap().withInt(inc, increment))
                .withConditionExpression("attribute_exists(" + PARTITION_KEY + ")");

        try {
            UpdateItemOutcome outcome = table.updateItem(spec);
        } catch (Exception e) {
            String error = "Could not update user count";
            System.out.println(error);
            throw new DataAccessException(Service.SERVER_ERROR_TAG + " " + error);
        }
    }
}
