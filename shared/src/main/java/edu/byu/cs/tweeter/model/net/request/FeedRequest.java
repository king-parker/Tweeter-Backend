package edu.byu.cs.tweeter.model.net.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;

public class FeedRequest {

    private AuthToken authToken;
    private String followerAlias;
    private int limit;
    private Status lastFeedStatus;

    private FeedRequest() { }

    public FeedRequest(AuthToken authToken, String followerAlias, int limit, Status lastFeedStatus) {
        this.authToken = authToken;
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

    public String getFollowerAlias() {
        return followerAlias;
    }

    public void setFollowerAlias(String followerAlias) {
        this.followerAlias = followerAlias;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public Status getLastFeedStatus() {
        return lastFeedStatus;
    }

    public void setLastFeedStatus(Status lastFeedStatus) {
        this.lastFeedStatus = lastFeedStatus;
    }
}