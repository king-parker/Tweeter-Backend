package edu.byu.cs.tweeter.model.net.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;

public class StoryRequest {

    private AuthToken authToken;
    private String currUserAlias;
    private String followerAlias;
    private Integer limit;
    private Status lastStoryStatus;

    private StoryRequest() {}

    public StoryRequest(AuthToken authToken, String currUserAlias, String followerAlias, Integer limit, Status lastStoryStatus) {
        this.authToken = authToken;
        this.currUserAlias = currUserAlias;
        this.followerAlias = followerAlias;
        this.limit = limit;
        this.lastStoryStatus = lastStoryStatus;
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

    public Status getLastStoryStatus() {
        return lastStoryStatus;
    }

    public void setLastStoryStatus(Status lastStoryStatus) {
        this.lastStoryStatus = lastStoryStatus;
    }
}
