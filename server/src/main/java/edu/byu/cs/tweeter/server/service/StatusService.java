package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.net.request.FeedRequest;
import edu.byu.cs.tweeter.model.net.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.net.request.StoryRequest;
import edu.byu.cs.tweeter.model.net.response.FeedResponse;
import edu.byu.cs.tweeter.model.net.response.PostStatusResponse;
import edu.byu.cs.tweeter.model.net.response.StoryResponse;
import edu.byu.cs.tweeter.server.util.Pair;

import java.util.List;

public class StatusService extends Service {

    public PostStatusResponse postStatus(PostStatusRequest request) {

        authenticateRequest(request);
        if (request.getStatus() == null) throw new RuntimeException(BAD_REQUEST_TAG + " No status provided");

        getStoryDAO().postStatus(request.getStatus());
        return new PostStatusResponse();
    }

    public FeedResponse getFeed(FeedRequest request) {

        authenticateRequest(request);
        if (request.getFollowerAlias() == null) throw new RuntimeException(BAD_REQUEST_TAG + " No user provided");
        if (request.getLimit() == null) throw new RuntimeException(BAD_REQUEST_TAG + " No provided limit");

        Pair<List<Status>, Boolean> pair = getFeedDAO().getFeed(request.getFollowerAlias(), request.getLimit(),
                request.getLastFeedStatus());
        List<Status> feed = pair.getFirst();
        Boolean hasMorePages = pair.getSecond();
        return new FeedResponse(feed, hasMorePages);
    }

    public StoryResponse getStory(StoryRequest request) {

        authenticateRequest(request);
        if (request.getFollowerAlias() == null) throw new RuntimeException(BAD_REQUEST_TAG + " No user provided");
        if (request.getLimit() == null) throw new RuntimeException(BAD_REQUEST_TAG + " No provided limit");

        Pair<List<Status>, Boolean> pair = getStoryDAO().getStory(request.getFollowerAlias(), request.getLimit(),
                request.getLastStoryStatus());
        List<Status> story = pair.getFirst();
        Boolean hasMorePages = pair.getSecond();
        return new StoryResponse(story, hasMorePages);
    }
}
