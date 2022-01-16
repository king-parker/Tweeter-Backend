package edu.byu.cs.tweeter.server.dao.dummy;

import edu.byu.cs.tweeter.server.dao.AuthDAO;
import edu.byu.cs.tweeter.server.dao.DAOFactory;
import edu.byu.cs.tweeter.server.dao.FeedDAO;
import edu.byu.cs.tweeter.server.dao.FollowDAO;
import edu.byu.cs.tweeter.server.dao.StoryDAO;
import edu.byu.cs.tweeter.server.dao.UserDAO;

public class DummyDAOFactory extends DAOFactory {
    @Override
    public AuthDAO getAuthDAO() {
        return new DummyAuthDAO();
    }

    @Override
    public FeedDAO getFeedDAO() {
        return new DummyFeedDAO();
    }

    @Override
    public FollowDAO getFollowDAO() {
        return new DummyFollowDAO();
    }

    @Override
    public StoryDAO getStoryDAO() {
        return new DummyStoryDAO();
    }

    @Override
    public UserDAO getUserDAO() {
        return new DummyUserDAO();
    }
}
