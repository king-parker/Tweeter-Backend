package edu.byu.cs.tweeter.server.dao;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.server.util.Pair;

import java.util.List;

public interface StoryDAO {
    void postStatus(Status status);
    int getStoryCount(User currUser);
    Pair<List<Status>, Boolean> getStory(String followerAlias, int limit, Status lastStoryStatus);
}
