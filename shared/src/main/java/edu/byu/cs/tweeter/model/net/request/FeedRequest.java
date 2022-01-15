package edu.byu.cs.tweeter.model.net.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;

public class FeedRequest extends AuthorizedRequest {

    private String followerAlias;
    private Integer limit;
    private Status lastFeedStatus;

    private FeedRequest() {super();}

    public FeedRequest(AuthToken authToken, String currUserAlias, int limit, Status lastFeedStatus) {
        super(authToken, currUserAlias);
        this.followerAlias = currUserAlias;
        this.limit = limit;
        this.lastFeedStatus = lastFeedStatus;
    }

    public FeedRequest(AuthToken authToken, String currUserAlias, String followerAlias, int limit, Status lastFeedStatus) {
        super(authToken, currUserAlias);
        this.followerAlias = followerAlias;
        this.limit = limit;
        this.lastFeedStatus = lastFeedStatus;
    }

    public String getFollowerAlias() {
        return followerAlias;
    }

    public void setFollowerAlias(String followerAlias) {
        this.followerAlias = followerAlias;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Status getLastFeedStatus() {
        return lastFeedStatus;
    }

    public void setLastFeedStatus(Status lastFeedStatus) {
        this.lastFeedStatus = lastFeedStatus;
    }
}
