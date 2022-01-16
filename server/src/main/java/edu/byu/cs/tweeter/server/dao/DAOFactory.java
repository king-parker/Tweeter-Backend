package edu.byu.cs.tweeter.server.dao;

public abstract class DAOFactory {
    static private DAOFactory instance;

    public static void setInstance(DAOFactory value) {
        instance = value;
    }

    public static DAOFactory getInstance() {
        return instance;
    }

    abstract public AuthDAO getAuthDAO();
    abstract public FeedDAO getFeedDAO();
    abstract public FollowDAO getFollowDAO();
    abstract public StoryDAO getStoryDAO();
    abstract public UserDAO getUserDAO();
}
