package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.net.request.AuthorizedRequest;
import edu.byu.cs.tweeter.server.dao.*;

public class Service {

    public static final String BAD_REQUEST_TAG = "[BadRequest]";
    public static final String AUTH_ERROR_TAG = "[AuthError]";
    public static final String NOT_FOUND_TAG = "[NotFound]";
    public static final String SERVER_ERROR_TAG = "[InternalServerError]";

    /**
     * Returns an instance of {@link AuthDAO}. Allows mocking of the AuthDAO class
     * for testing purposes. All usages of AuthDAO should get their AuthDAO
     * instance from this method to allow for mocking of the instance.
     *
     * @return the instance.
     */
    public AuthDAO getAuthDAO() {
        return new AuthDAO();
    }

    /**
     * Returns an instance of {@link FeedDAO}. Allows mocking of the FeedDAO class
     * for testing purposes. All usages of FeedDAO should get their FeedDAO
     * instance from this method to allow for mocking of the instance.
     *
     * @return the instance.
     */
    public FeedDAO getFeedDAO() {
        return new FeedDAO();
    }

    /**
     * Returns an instance of {@link FollowDAO}. Allows mocking of the FollowDAO class
     * for testing purposes. All usages of FollowDAO should get their FollowDAO
     * instance from this method to allow for mocking of the instance.
     *
     * @return the instance.
     */
    public FollowDAO getFollowingDAO() {
        return new FollowDAO();
    }

    /**
     * Returns an instance of {@link StoryDAO}. Allows mocking of the StoryDAO class
     * for testing purposes. All usages of StoryDAO should get their StoryDAO
     * instance from this method to allow for mocking of the instance.
     *
     * @return the instance.
     */
    public StoryDAO getStoryDAO() {
        return new StoryDAO();
    }

    /**
     * Returns an instance of {@link UserDAO}. Allows mocking of the UserDAO class
     * for testing purposes. All usages of UserDAO should get their UserDAO
     * instance from this method to allow for mocking of the instance.
     *
     * @return the instance.
     */
    public UserDAO getUserDAO() {
        return new UserDAO();
    }

    public void authenticateRequest(AuthorizedRequest request) {
        if (request.getCurrUserAlias() == null) throw new RuntimeException(BAD_REQUEST_TAG + " No current user listed");
        if (!getAuthDAO().isValidAuthToken(request.getCurrUserAlias(), request.getAuthToken()))
            throw new RuntimeException(AUTH_ERROR_TAG + " Unauthenticated request");
    }
}
