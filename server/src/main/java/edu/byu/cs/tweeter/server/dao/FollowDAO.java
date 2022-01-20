package edu.byu.cs.tweeter.server.dao;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.server.util.Pair;

import java.util.List;

public interface FollowDAO {
    boolean isFollower(String followerAlias, String followeeAlias);
    void follow(String followerAlias, String followeeAlias);
    void unfollow(String followerAlias, String followeeAlias);
    int getFolloweeCount(String followerAlias);
    Pair<List<User>, Boolean> getFollowees(String followerAlias, int limit, String lastFolloweeAlias);
    int getFollowerCount(String followeeAlias);
    Pair<List<User>, Boolean> getFollowers(String followeeAlias, int limit, String lastFollowerAlias);
}
