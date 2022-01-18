package edu.byu.cs.tweeter.server.dao.dynamo;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.PutItemSpec;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.DataAccessException;
import edu.byu.cs.tweeter.server.dao.UserDAO;
import edu.byu.cs.tweeter.server.service.Service;

import java.util.Arrays;

public class DynamoUserDAO implements UserDAO {
    private final String TABLE_NAME = "tweeter-user";
    private final String REGION = "us-west-2";
    private final String PARTITION_KEY = "user_alias";
    private final String ATT_FN_NAME = "first_name";
    private final String ATT_LN_NAME = "last_name";
    private final String ATT_PASS_NAME = "password";
    private final String ATT_IMURL_NAME = "image_url";

    private AmazonDynamoDB client;
    private DynamoDB db;
    private Table table;

    public DynamoUserDAO() {
        client = AmazonDynamoDBClientBuilder.standard()
                .withRegion(REGION).build();
        db = new DynamoDB(client);
        table = db.getTable(TABLE_NAME);
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

        String imageUrl = new S3DAO().upload(username, imageBytesBase64);

        Item item = new Item().withPrimaryKey(PARTITION_KEY, username)
                .withString(ATT_FN_NAME, firstName)
                .withString(ATT_LN_NAME, lastName)
                .withString(ATT_PASS_NAME, password)
                .withString(ATT_IMURL_NAME, imageUrl);
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
}
