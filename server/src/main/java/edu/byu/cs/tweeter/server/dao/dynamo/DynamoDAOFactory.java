package edu.byu.cs.tweeter.server.dao.dynamo;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import edu.byu.cs.tweeter.server.dao.*;

public class DynamoDAOFactory extends DAOFactory {

    public static final String REGION = "us-west-2";
    private static AmazonDynamoDB client;
    private static DynamoDB db;

    public static AmazonDynamoDB getDbClient() {
        if (client == null) {
            client = AmazonDynamoDBClientBuilder.standard()
                    .withRegion(REGION).build();
        }

        return client;
    }

    public static DynamoDB getDatabase() {
        if (db == null) {
            db = new DynamoDB(getDbClient());
        }

        return db;
    }

    public DynamoDAOFactory() {
        getDatabase();
    }

    @Override
    public DynamoAuthDAO getAuthDAO() {
        return new DynamoAuthDAO();
    }

    @Override
    public FeedDAO getFeedDAO() {
        return new DynamoFeedDAO();
    }

    @Override
    public FollowDAO getFollowDAO() {
        return new DynamoFollowDAO();
    }

    @Override
    public StoryDAO getStoryDAO() {
        return new DynamoStoryDAO();
    }

    @Override
    public UserDAO getUserDAO() {
        return new DynamoUserDAO();
    }
}
