package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.net.request.FeedRequest;
import edu.byu.cs.tweeter.model.net.response.FeedResponse;
import edu.byu.cs.tweeter.server.dao.FeedDAO;

public class StatusService extends Service {

    public FeedResponse getFeed(FeedRequest request) {
        return getFeedDAO().getFeed(request);
    }
}
