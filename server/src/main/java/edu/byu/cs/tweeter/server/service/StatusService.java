package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.net.request.FeedRequest;
import edu.byu.cs.tweeter.model.net.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.net.request.StoryRequest;
import edu.byu.cs.tweeter.model.net.response.FeedResponse;
import edu.byu.cs.tweeter.model.net.response.PostStatusResponse;
import edu.byu.cs.tweeter.model.net.response.StoryResponse;

public class StatusService extends Service {

    public PostStatusResponse postStatus(PostStatusRequest request) {

        authenticateRequest(request);
        if (request.getStatus() == null) throw new RuntimeException(BAD_REQUEST_TAG + " No status provided");

        return getStoryDAO().postStatus(request.getStatus());
    }

    public FeedResponse getFeed(FeedRequest request) {

        authenticateRequest(request);
        if (request.getFollowerAlias() == null) throw new RuntimeException(BAD_REQUEST_TAG + " No user provided");
        if (request.getLimit() == null) throw new RuntimeException(BAD_REQUEST_TAG + " No provided limit");

        return getFeedDAO().getFeed(request);
    }

    public StoryResponse getStory(StoryRequest request) {

        authenticateRequest(request);
        if (request.getFollowerAlias() == null) throw new RuntimeException(BAD_REQUEST_TAG + " No user provided");
        if (request.getLimit() == null) throw new RuntimeException(BAD_REQUEST_TAG + " No provided limit");

        return getStoryDAO().getStory(request);
    }
}
