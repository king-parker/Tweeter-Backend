package edu.byu.cs.tweeter.server.dao;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.FeedRequest;
import edu.byu.cs.tweeter.model.net.response.FeedResponse;
import edu.byu.cs.tweeter.server.util.Pair;

import java.util.List;

public interface FeedDAO {
    Integer getFeedCount(User currUser);
    Pair<List<Status>, Boolean> getFeed(String followerAlias, int limit, Status lastFeedStatus);
}
