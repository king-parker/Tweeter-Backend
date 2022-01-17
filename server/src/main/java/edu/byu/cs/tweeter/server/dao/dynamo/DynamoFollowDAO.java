package edu.byu.cs.tweeter.server.dao.dynamo;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.server.dao.FollowDAO;
import edu.byu.cs.tweeter.server.dao.dummy.DummyFollowDAO;
import edu.byu.cs.tweeter.server.util.Pair;

import java.util.List;

public class DynamoFollowDAO implements FollowDAO {
    @Override
    public boolean isFollower(String followerAlias, String followeeAlias) {
        return new DynamoFollowDAO().isFollower(followerAlias, followeeAlias);
    }

    @Override
    public void follow(String followerAlias, String followeeAlias) {

    }

    @Override
    public void unfollow(String followerAlias, String followeeAlias) {

    }

    @Override
    public int getFolloweeCount(String followerAlias) {
        return new DynamoFollowDAO().getFolloweeCount(followerAlias);
    }

    @Override
    public Pair<List<User>, Boolean> getFollowees(String followerAlias, int limit, String lastFolloweeAlias) {
        return new DynamoFollowDAO().getFollowees(followerAlias, limit, lastFolloweeAlias);
    }

    @Override
    public int getFollowerCount(String followeeAlias) {
        return new DynamoFollowDAO().getFolloweeCount(followeeAlias);
    }

    @Override
    public Pair<List<User>, Boolean> getFollowers(String followeeAlias, int limit, String lastFollowerAlias) {
        return new DummyFollowDAO().getFollowers(followeeAlias, limit, lastFollowerAlias);
    }
}
