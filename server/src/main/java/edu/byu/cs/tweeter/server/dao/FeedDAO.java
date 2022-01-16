package edu.byu.cs.tweeter.server.dao;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.server.util.Pair;

import java.util.List;

public interface FeedDAO {
    Integer getFeedCount(String userAlias);
    Pair<List<Status>, Boolean> getFeed(String followerAlias, int limit, Status lastFeedStatus);
}
