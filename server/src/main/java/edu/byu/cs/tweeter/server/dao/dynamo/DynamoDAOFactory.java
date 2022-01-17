package edu.byu.cs.tweeter.server.dao.dynamo;

import edu.byu.cs.tweeter.server.dao.*;

public class DynamoDAOFactory extends DAOFactory {

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
