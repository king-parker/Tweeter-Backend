package edu.byu.cs.tweeter.server.dao.dummy;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.FeedRequest;
import edu.byu.cs.tweeter.model.net.response.FeedResponse;
import edu.byu.cs.tweeter.server.dao.FeedDAO;
import edu.byu.cs.tweeter.server.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class DummyFeedDAO extends DummyDAO implements FeedDAO {

    @Override
    public Integer getFeedCount(User currUser) {
        // TODO: uses the dummy data.  Replace with a real implementation.
        assert currUser != null;
        return getDummyFeed().size();
    }

    @Override
    public Pair<List<Status>, Boolean> getFeed(String followerAlias, int limit, Status lastFeedStatus) {
        // TODO: Generates dummy data. Replace with a real implementation.
        assert limit > 0;
        assert followerAlias != null;

        List<Status> feed = getDummyFeed();
        List<Status> responseFeed = new ArrayList<>(limit);

        boolean hasMorePages = false;

        if(limit > 0) {
            if (feed != null) {
                int feedIndex = getFeedStatusStartingIndex(lastFeedStatus, feed);

                for(int limitCounter = 0; feedIndex < feed.size() && limitCounter < limit; feedIndex++, limitCounter++) {
                    responseFeed.add(feed.get(feedIndex));
                }

                hasMorePages = feedIndex < feed.size();
            }
        }


        return new Pair<>(responseFeed, hasMorePages);
    }

    private int getFeedStatusStartingIndex(Status lastFeedStatus, List<Status> feed) {

        int feedStatusIndex = 0;

        if(lastFeedStatus != null) {
            // This is a paged request for something after the first page. Find the first item
            // we should return
            for (int i = 0; i < feed.size(); i++) {
                if(lastFeedStatus.equals(feed.get(i))) {
                    // We found the index of the last item returned last time. Increment to get
                    // to the first one we should return
                    feedStatusIndex = i + 1;
                    break;
                }
            }
        }

        return feedStatusIndex;
    }
}
