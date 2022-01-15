package edu.byu.cs.tweeter.model.net.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;

public class FollowerRequest extends AuthorizedRequest {

    private String followeeAlias;
    private Integer limit;
    private String lastFollowerAlias;

    private FollowerRequest() {super();}

    public FollowerRequest(AuthToken authToken, String currUserAlias, int limit, String lastFollowerAlias) {
        super(authToken, currUserAlias);
        this.followeeAlias = currUserAlias;
        this.limit = limit;
        this.lastFollowerAlias = lastFollowerAlias;
    }

    public FollowerRequest(AuthToken authToken, String currUserAlias, String followeeAlias, Integer limit, String lastFollowerAlias) {
        super(authToken, currUserAlias);
        this.followeeAlias = followeeAlias;
        this.limit = limit;
        this.lastFollowerAlias = lastFollowerAlias;
    }

    public String getFolloweeAlias() {
        return followeeAlias;
    }

    public void setFolloweeAlias(String followeeAlias) {
        this.followeeAlias = followeeAlias;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public String getLastFollowerAlias() {
        return lastFollowerAlias;
    }

    public void setLastFollowerAlias(String lastFollowerAlias) {
        this.lastFollowerAlias = lastFollowerAlias;
    }
}
