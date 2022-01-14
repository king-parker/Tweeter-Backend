package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.net.request.*;
import edu.byu.cs.tweeter.model.net.response.*;
import edu.byu.cs.tweeter.server.dao.FollowDAO;

/**
 * Contains the business logic for getting the users a user is following.
 */
public class FollowService extends Service {

    /**
     * Returns the users that the user specified in the request is following. Uses information in
     * the request object to limit the number of followees returned and to return the next set of
     * followees after any that were returned in a previous request. Uses the {@link FollowDAO} to
     * get the followees.
     *
     * @param request contains the data required to fulfill the request.
     * @return the followees.
     */
    public FollowingResponse getFollowees(FollowingRequest request) {

        if (request.getCurrUserAlias() == null) throw new RuntimeException(BAD_REQUEST_TAG + " No current user listed");
        if (request.getFollowerAlias() == null) throw new RuntimeException(BAD_REQUEST_TAG + " No user provided");
        if (request.getLimit() == null) throw new RuntimeException(BAD_REQUEST_TAG + " No provided limit");
        if (!getAuthDAO().isValidAuthToken(request.getCurrUserAlias(), request.getAuthToken()))
            throw new RuntimeException(AUTH_ERROR_TAG + " Unauthenticated request");

        return getFollowingDAO().getFollowees(request);
    }

    /**
     * Returns the users that the user specified in the request is followed by. Uses information in
     * the request object to limit the number of followers returned and to return the next set of
     * followers after any that were returned in a previous request. Uses the {@link FollowDAO} to
     * get the followers.
     *
     * @param request contains the data required to fulfill the request.
     * @return the followers.
     */
    public FollowerResponse getFollowers(FollowerRequest request) {

        if (request.getCurrUserAlias() == null) throw new RuntimeException(BAD_REQUEST_TAG + " No current user listed");
        if (request.getFolloweeAlias() == null) throw new RuntimeException(BAD_REQUEST_TAG + " No user provided");
        if (request.getLimit() == null) throw new RuntimeException(BAD_REQUEST_TAG + " No provided limit");
        if (!getAuthDAO().isValidAuthToken(request.getCurrUserAlias(), request.getAuthToken()))
            throw new RuntimeException(AUTH_ERROR_TAG + " Unauthenticated request");

        return getFollowingDAO().getFollowers(request);
    }

    public FollowingCountResponse getFollowingCount(FollowingCountRequest request) {

        if (request.getCurrUserAlias() == null) throw new RuntimeException(BAD_REQUEST_TAG + " No current user listed");
        if (!getAuthDAO().isValidAuthToken(request.getCurrUserAlias(), request.getAuthToken()))
            throw new RuntimeException(AUTH_ERROR_TAG + " Unauthenticated request");

        int count = getFollowingDAO().getFolloweeCount(request.getCurrUserAlias());
        return new FollowingCountResponse(count);
    }

    public FollowersCountResponse getFollowersCount(FollowersCountRequest request) {

        if (request.getCurrUserAlias() == null) throw new RuntimeException(BAD_REQUEST_TAG + " No current user listed");
        if (!getAuthDAO().isValidAuthToken(request.getCurrUserAlias(), request.getAuthToken()))
            throw new RuntimeException(AUTH_ERROR_TAG + " Unauthenticated request");

        int count = getFollowingDAO().getFollowerCount(request.getCurrUserAlias());
        return new FollowersCountResponse(count);
    }

    public IsFollowingResponse isFollower(IsFollowingRequest request) {

        if (request.getCurrUserAlias() == null) throw new RuntimeException(BAD_REQUEST_TAG + " No current user listed");
        if (!getAuthDAO().isValidAuthToken(request.getCurrUserAlias(), request.getAuthToken()))
            throw new RuntimeException(AUTH_ERROR_TAG + " Unauthenticated request");
        if (request.getFollowerAlias() == null) throw new RuntimeException(BAD_REQUEST_TAG + " No following user listed");
        if (request.getFolloweeAlias() == null) throw new RuntimeException(BAD_REQUEST_TAG + " No followed user listed");

        boolean isFollower = getFollowingDAO().isFollower(request.getFollowerAlias(), request.getFolloweeAlias());
        return new IsFollowingResponse(isFollower);
    }
}
