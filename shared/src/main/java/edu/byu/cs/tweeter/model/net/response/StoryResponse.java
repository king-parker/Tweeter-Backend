package edu.byu.cs.tweeter.model.net.response;

import edu.byu.cs.tweeter.model.domain.Status;

import java.util.List;
import java.util.Objects;

public class StoryResponse extends PagedResponse {

    private List<Status> story;

    public StoryResponse(String message) {
        super(false, message, false);
    }

    public StoryResponse(List<Status> story, boolean hasMorePages) {
        super(true, hasMorePages);
        this.story = story;
    }

    public List<Status> getStory() {
        return story;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StoryResponse that = (StoryResponse) o;
        return (Objects.equals(story, that.story) &&
                Objects.equals(this.getMessage(), that.getMessage()) &&
                this.isSuccess() == that.isSuccess());
    }

    @Override
    public int hashCode() {
        return Objects.hash(story);
    }
}
