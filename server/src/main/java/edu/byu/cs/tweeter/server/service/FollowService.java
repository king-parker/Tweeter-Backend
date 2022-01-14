package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.net.request.FollowingRequest;
import edu.byu.cs.tweeter.model.net.response.FollowingResponse;
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
}
