package edu.byu.cs.tweeter.model.net.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;

public class FeedRequest {

    private AuthToken authToken;
    private String currUserAlias;
    private String followerAlias;
    private Integer limit;
    private Status lastFeedStatus;

    private FeedRequest() { }

    public FeedRequest(AuthToken authToken, String currUserAlias, String followerAlias, int limit, Status lastFeedStatus) {
        this.authToken = authToken;
        this.currUserAlias = currUserAlias;
        this.followerAlias = followerAlias;
        this.limit = limit;
        this.lastFeedStatus = lastFeedStatus;
    }

    public AuthToken getAuthToken() {
        return authToken;
    }

    public void setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
    }

    public String getCurrUserAlias() {
        return currUserAlias;
    }

    public void setCurrUserAlias(String currUserAlias) {
        this.currUserAlias = currUserAlias;
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
