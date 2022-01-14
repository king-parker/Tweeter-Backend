package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.net.request.FeedRequest;
import edu.byu.cs.tweeter.model.net.response.FeedResponse;

public class StatusService extends Service {

    public FeedResponse getFeed(FeedRequest request) {

        if (request.getCurrUserAlias() == null) throw new RuntimeException(BAD_REQUEST_TAG + " No current user listed");
        if (request.getFollowerAlias() == null) throw new RuntimeException(BAD_REQUEST_TAG + " No user provided");
        if (request.getLimit() == null) throw new RuntimeException(BAD_REQUEST_TAG + " No provided limit");
        if (!getAuthDAO().isValidAuthToken(request.getCurrUserAlias(), request.getAuthToken()))
            throw new RuntimeException(AUTH_ERROR_TAG + " Unauthenticated request");

        return getFeedDAO().getFeed(request);
    }
}
