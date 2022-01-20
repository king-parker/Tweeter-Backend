package edu.byu.cs.tweeter.server.dao.dummy;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.server.dao.FollowDAO;
import edu.byu.cs.tweeter.server.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * A DAO for accessing 'following' data from the database.
 */
public class DummyFollowDAO extends DummyDAO implements FollowDAO {

    @Override
    public boolean isFollower(String followerAlias, String followeeAlias) {
        // uses the dummy data.  Replace with a real implementation.
        assert followerAlias != null;
        assert followeeAlias != null;
        return followerAlias.length() > followeeAlias.length();
    }

    @Override
    public void follow(String followerAlias, String followeeAlias) {
        // Doesn't do anything.  Replace with a real implementation.
        assert followerAlias != null;
        assert followeeAlias != null;
    }

    @Override
    public void unfollow(String followerAlias, String followeeAlias) {
        // Doesn't do anything.  Replace with a real implementation.
        assert followerAlias != null;
        assert followeeAlias != null;
    }

    /**
     * Gets the count of users from the database that the user specified is following. The
     * current implementation uses generated data and doesn't actually access a database.
     *
     * @param followerAlias the User whose count of how many following is desired.
     * @return said count.
     */
    @Override
    public int getFolloweeCount(String followerAlias) {
        // uses the dummy data.  Replace with a real implementation.
        assert followerAlias != null;
        return getDummyFollowees().size() + 1;
    }

    @Override
    public Pair<List<User>, Boolean> getFollowees(String followerAlias, int limit, String lastFolloweeAlias) {
        // Generates dummy data. Replace with a real implementation.
        assert limit > 0;
        assert followerAlias != null;

        List<User> allFollowees = getDummyFollowees();
        List<User> responseFollowees = new ArrayList<>(limit);

        boolean hasMorePages = false;

        if(limit > 0) {
            if (allFollowees != null) {
                int followeesIndex = getFolloweesStartingIndex(lastFolloweeAlias, allFollowees);

                for(int limitCounter = 0; followeesIndex < allFollowees.size() && limitCounter < limit; followeesIndex++, limitCounter++) {
                    responseFollowees.add(allFollowees.get(followeesIndex));
                }

                hasMorePages = followeesIndex < allFollowees.size();
            }
        }

        return new Pair<>(responseFollowees, hasMorePages);
    }

    /**
     * Determines the index for the first followee in the specified 'allFollowees' list that should
     * be returned in the current request. This will be the index of the next followee after the
     * specified 'lastFollowee'.
     *
     * @param lastFolloweeAlias the alias of the last followee that was returned in the previous
     *                          request or null if there was no previous request.
     * @param allFollowees the generated list of followees from which we are returning paged results.
     * @return the index of the first followee to be returned.
     */
    private int getFolloweesStartingIndex(String lastFolloweeAlias, List<User> allFollowees) {

        int followeesIndex = 0;

        if(lastFolloweeAlias != null) {
            // This is a paged request for something after the first page. Find the first item
            // we should return
            for (int i = 0; i < allFollowees.size(); i++) {
                if(lastFolloweeAlias.equals(allFollowees.get(i).getAlias())) {
                    // We found the index of the last item returned last time. Increment to get
                    // to the first one we should return
                    followeesIndex = i + 1;
                    break;
                }
            }
        }

        return followeesIndex;
    }

    @Override
    public int getFollowerCount(String followeeAlias) {
        // uses the dummy data.  Replace with a real implementation.
        assert followeeAlias != null;
        return getDummyFollowers().size() - 1;
    }

    @Override
    public Pair<List<User>, Boolean> getFollowers(String followeeAlias, int limit, String lastFollowerAlias) {
        // Generates dummy data. Replace with a real implementation.
        assert limit > 0;
        assert followeeAlias != null;

        List<User> allFollowers = getDummyFollowers();
        List<User> responseFollowers = new ArrayList<>(limit);

        boolean hasMorePages = false;

        if(limit > 0) {
            if (allFollowers != null) {
                int followersIndex = getFollowersStartingIndex(lastFollowerAlias, allFollowers);

                for(int limitCounter = 0; followersIndex < allFollowers.size() && limitCounter < limit; followersIndex++, limitCounter++) {
                    responseFollowers.add(allFollowers.get(followersIndex));
                }

                hasMorePages = followersIndex < allFollowers.size();
            }
        }

        return new Pair<>(responseFollowers, hasMorePages);
    }

    private int getFollowersStartingIndex(String lastFollowerAlias, List<User> allFollowers) {

        int followersIndex = 0;

        if(lastFollowerAlias != null) {
            // This is a paged request for something after the first page. Find the first item
            // we should return
            for (int i = 0; i < allFollowers.size(); i++) {
                if(lastFollowerAlias.equals(allFollowers.get(i).getAlias())) {
                    // We found the index of the last item returned last time. Increment to get
                    // to the first one we should return
                    followersIndex = i + 1;
                    break;
                }
            }
        }

        return followersIndex;
    }
}
