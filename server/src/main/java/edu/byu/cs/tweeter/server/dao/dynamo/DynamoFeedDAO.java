package edu.byu.cs.tweeter.server.dao.dynamo;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.server.dao.FeedDAO;
import edu.byu.cs.tweeter.server.dao.dummy.DummyFeedDAO;
import edu.byu.cs.tweeter.server.util.Pair;

import java.util.List;

public class DynamoFeedDAO implements FeedDAO {
    @Override
    public Integer getFeedCount(String userAlias) {
        return new DummyFeedDAO().getFeedCount(userAlias);
    }

    @Override
    public Pair<List<Status>, Boolean> getFeed(String followerAlias, int limit, Status lastFeedStatus) {
        return new DummyFeedDAO().getFeed(followerAlias, limit, lastFeedStatus);
    }
}
