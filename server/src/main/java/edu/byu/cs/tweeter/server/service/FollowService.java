package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.*;
import edu.byu.cs.tweeter.model.net.response.*;
import edu.byu.cs.tweeter.server.dao.DAOFactory;
import edu.byu.cs.tweeter.server.util.Pair;

import java.util.List;

/**
 * Contains the business logic for getting the users a user is following.
 */
public class FollowService extends Service {
    public FollowService() {
        super();
    }

    public FollowService(DAOFactory daoFactory) {
        super(daoFactory);
    }

    public FollowingResponse getFollowees(FollowingRequest request) {

        authenticateRequest(request);
        if (request.getFollowerAlias() == null) throw new RuntimeException(BAD_REQUEST_TAG + " No selected user provided");
        if (request.getLimit() == null) throw new RuntimeException(BAD_REQUEST_TAG + " No provided limit");

        if (getFollowingDAO().getFolloweeCount(request.getFollowerAlias()) > 0) {
            Pair<List<User>, Boolean> pair = getFollowingDAO().getFollowees(request.getFollowerAlias(), request.getLimit(),
                    request.getLastFolloweeAlias());
            List<User> followees = pair.getFirst();
            Boolean hasMorePages = pair.getSecond();
            return new FollowingResponse(followees, hasMorePages);
        }
        else return new FollowingResponse(null, false);
    }

    public FollowerResponse getFollowers(FollowerRequest request) {

        authenticateRequest(request);
        if (request.getFolloweeAlias() == null) throw new RuntimeException(BAD_REQUEST_TAG + " No selected user provided");
        if (request.getLimit() == null) throw new RuntimeException(BAD_REQUEST_TAG + " No provided limit");

        if (getFollowingDAO().getFollowerCount(request.getFolloweeAlias()) > 0) {
            Pair<List<User>, Boolean> pair = getFollowingDAO().getFollowers(request.getFolloweeAlias(), request.getLimit(),
                    request.getLastFollowerAlias());
            List<User> followers = pair.getFirst();
            Boolean hasMorePages = pair.getSecond();
            return new FollowerResponse(followers, hasMorePages);
        }
        else return new FollowerResponse(null, false);
    }

    public FollowingCountResponse getFollowingCount(FollowingCountRequest request) {

        authenticateRequest(request);
        if (request.getFollowerAlias() == null) throw new RuntimeException(BAD_REQUEST_TAG + " No selected user provided");

        int count = getFollowingDAO().getFolloweeCount(request.getCurrUserAlias());
        return new FollowingCountResponse(count);
    }

    public FollowersCountResponse getFollowersCount(FollowersCountRequest request) {

        authenticateRequest(request);
        if (request.getFolloweeAlias() == null) throw new RuntimeException(BAD_REQUEST_TAG + " No selected user provided");

        int count = getFollowingDAO().getFollowerCount(request.getCurrUserAlias());
        return new FollowersCountResponse(count);
    }

    public IsFollowingResponse isFollower(IsFollowingRequest request) {

        authenticateRequest(request);
        if (request.getFollowerAlias() == null) throw new RuntimeException(BAD_REQUEST_TAG + " No following user listed");
        if (request.getFolloweeAlias() == null) throw new RuntimeException(BAD_REQUEST_TAG + " No followed user listed");

        boolean isFollower = getFollowingDAO().isFollower(request.getFollowerAlias(), request.getFolloweeAlias());
        return new IsFollowingResponse(isFollower);
    }

    public FollowResponse follow(FollowRequest request) {

        authenticateRequest(request);
        if (request.getFollowerAlias() == null) throw new RuntimeException(BAD_REQUEST_TAG + " No following user listed");
        if (request.getFolloweeAlias() == null) throw new RuntimeException(BAD_REQUEST_TAG + " No followed user listed");

        getFollowingDAO().follow(request.getFollowerAlias(), request.getFolloweeAlias());
        return new FollowResponse();
    }

    public UnfollowResponse unfollow(UnfollowRequest request) {

        authenticateRequest(request);
        if (request.getFollowerAlias() == null) throw new RuntimeException(BAD_REQUEST_TAG + " No following user listed");
        if (request.getUnfolloweeAlias() == null) throw new RuntimeException(BAD_REQUEST_TAG + " No unfollowed user listed");

        getFollowingDAO().unfollow(request.getFollowerAlias(), request.getUnfolloweeAlias());
        return new UnfollowResponse();
    }
}
