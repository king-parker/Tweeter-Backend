package edu.byu.cs.tweeter.server.dao.dummy;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.server.dao.StoryDAO;
import edu.byu.cs.tweeter.server.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class DummyStoryDAO extends DummyDAO implements StoryDAO {

    @Override
    public void postStatus(Status status) {
        // TODO: Doesn't do anything.  Replace with a real implementation.
    }

    @Override
    public int getStoryCount(String userAlias) {
        // TODO: uses the dummy data.  Replace with a real implementation.
        assert userAlias != null;
        return getDummyStory().size();
    }

    @Override
    public Pair<List<Status>, Boolean> getStory(String followerAlias, int limit, Status lastStoryStatus) {
        // TODO: Generates dummy data. Replace with a real implementation.
        assert limit > 0;
        assert followerAlias != null;

        List<Status> story = getDummyStory();
        List<Status> responseStory = new ArrayList<>(limit);

        boolean hasMorePages = false;

        if(limit > 0) {
            if (story != null) {
                int storyIndex = getStoryStatusStartingIndex(lastStoryStatus, story);

                for(int limitCounter = 0; storyIndex < story.size() && limitCounter < limit; storyIndex++, limitCounter++) {
                    responseStory.add(story.get(storyIndex));
                }

                hasMorePages = storyIndex < story.size();
            }
        }

        return new Pair<>(responseStory, hasMorePages);
    }

    private int getStoryStatusStartingIndex(Status lastStoryStatus, List<Status> story) {

        int storyStatusIndex = 0;

        if(lastStoryStatus != null) {
            // This is a paged request for something after the first page. Find the first item
            // we should return
            for (int i = 0; i < story.size(); i++) {
                if(lastStoryStatus.equals(story.get(i))) {
                    // We found the index of the last item returned last time. Increment to get
                    // to the first one we should return
                    storyStatusIndex = i + 1;
                    break;
                }
            }
        }

        return storyStatusIndex;
    }
}
