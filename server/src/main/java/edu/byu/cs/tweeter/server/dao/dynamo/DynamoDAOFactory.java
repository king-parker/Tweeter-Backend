package edu.byu.cs.tweeter.server.dao.dynamo;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import edu.byu.cs.tweeter.server.dao.*;

public class DynamoDAOFactory extends DAOFactory {

    public static final String REGION = "us-west-2";
    private static DynamoDB db;

    public static DynamoDB getDatabase() {
        if (db == null) {
            AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
                    .withRegion(REGION).build();
            db = new DynamoDB(client);
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
