package edu.byu.cs.tweeter.server.dao;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.StoryRequest;
import edu.byu.cs.tweeter.model.net.response.StoryResponse;

import java.util.ArrayList;
import java.util.List;

public class StoryDAO extends DAO {

    public Integer getStoryCount(User currUser) {
        // TODO: uses the dummy data.  Replace with a real implementation.
        assert currUser != null;
        return getDummyStory().size();
    }

    public StoryResponse getStory(StoryRequest request) {
        // TODO: Generates dummy data. Replace with a real implementation.
        assert request.getLimit() > 0;
        assert request.getFollowerAlias() != null;

        List<Status> story = getDummyStory();
        List<Status> responseStory = new ArrayList<>(request.getLimit());

        boolean hasMorePages = false;

        if(request.getLimit() > 0) {
            if (story != null) {
                int storyIndex = getStoryStatusStartingIndex(request.getLastStoryStatus(), story);

                for(int limitCounter = 0; storyIndex < story.size() && limitCounter < request.getLimit(); storyIndex++, limitCounter++) {
                    responseStory.add(story.get(storyIndex));
                }

                hasMorePages = storyIndex < story.size();
            }
        }

        return new StoryResponse(responseStory, hasMorePages);
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
