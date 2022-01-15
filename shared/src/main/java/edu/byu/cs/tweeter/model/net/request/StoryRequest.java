package edu.byu.cs.tweeter.model.net.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;

public class StoryRequest extends AuthorizedRequest {
    private String followerAlias;
    private Integer limit;
    private Status lastStoryStatus;

    private StoryRequest() {super();}

    public StoryRequest(AuthToken authToken, String currUserAlias, int limit, Status lastStoryStatus) {
        super(authToken, currUserAlias);
        this.followerAlias = currUserAlias;
        this.limit = limit;
        this.lastStoryStatus = lastStoryStatus;
    }

    public StoryRequest(AuthToken authToken, String currUserAlias, String followerAlias, Integer limit, Status lastStoryStatus) {
        super(authToken, currUserAlias);
        this.followerAlias = followerAlias;
        this.limit = limit;
        this.lastStoryStatus = lastStoryStatus;
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
