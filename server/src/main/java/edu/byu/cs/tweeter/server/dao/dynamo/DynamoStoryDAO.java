package edu.byu.cs.tweeter.server.dao.dynamo;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.server.dao.StoryDAO;
import edu.byu.cs.tweeter.server.dao.dummy.DummyStoryDAO;
import edu.byu.cs.tweeter.server.util.Pair;

import java.util.List;

public class DynamoStoryDAO implements StoryDAO {
    @Override
    public void postStatus(Status status) {

    }

    @Override
    public int getStoryCount(String userAlias) {
        return new DummyStoryDAO().getStoryCount(userAlias);
    }

    @Override
    public Pair<List<Status>, Boolean> getStory(String followerAlias, int limit, Status lastStoryStatus) {
        return new DummyStoryDAO().getStory(followerAlias, limit, lastStoryStatus);
    }
}
