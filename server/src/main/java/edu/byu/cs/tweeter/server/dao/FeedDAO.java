package edu.byu.cs.tweeter.server.dao;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.FeedRequest;
import edu.byu.cs.tweeter.model.net.response.FeedResponse;
import edu.byu.cs.tweeter.server.util.FakeData;

import java.util.ArrayList;
import java.util.List;

public class FeedDAO {

    public Integer getFeedCount(User currUser) {
        // TODO: uses the dummy data.  Replace with a real implementation.
        assert currUser != null;
        return getDummyFeed().size();
    }

    public FeedResponse getFeed(FeedRequest request) {
        // TODO: Generates dummy data. Replace with a real implementation.
        assert request.getLimit() > 0;
        assert request.getFollowerAlias() != null;

        List<Status> feed = getDummyFeed();
        List<Status> responseFeed = new ArrayList<>(request.getLimit());

        boolean hasMorePages = false;

        if(request.getLimit() > 0) {
            if (feed != null) {
                int feedIndex = getFeedStatusStartingIndex(request.getLastFeedStatus(), feed);

                for(int limitCounter = 0; feedIndex < feed.size() && limitCounter < request.getLimit(); feedIndex++, limitCounter++) {
                    responseFeed.add(feed.get(feedIndex));
                }

                hasMorePages = feedIndex < feed.size();
            }
        }

        return new FeedResponse(responseFeed, hasMorePages);
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

    List<Status> getDummyFeed() {
        return getFakeData().getFakeStatuses();
    }

    FakeData getFakeData() {
        return new FakeData();
    }
}
