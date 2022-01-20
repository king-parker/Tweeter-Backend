package edu.byu.cs.tweeter.model.net.response;

import edu.byu.cs.tweeter.model.domain.Status;

import java.util.List;
import java.util.Objects;

public class FeedResponse extends PagedResponse {

    private List<Status> feed;

    public FeedResponse(String message) {
        super(false, message, false);
    }

    public FeedResponse(List<Status> feed, boolean hasMorePages) {
        super(true, hasMorePages);
        this.feed = feed;
    }

    public List<Status> getFeed() {
        return feed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FeedResponse that = (FeedResponse) o;
        return (Objects.equals(feed, that.feed) &&
                Objects.equals(this.getMessage(), that.getMessage()) &&
                this.isSuccess() == that.isSuccess());
    }

    @Override
    public int hashCode() {
        return Objects.hash(feed);
    }

    @Override
    public String toString() {
        return "FeedResponse{" +
                "success=" + this.isSuccess() +
                ", message='" + this.getMessage() + '\'' +
                ", hasMorePages=" + this.getHasMorePages() +
                ", feed=" + feed +
                "} ";
    }
}
