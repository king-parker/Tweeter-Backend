package edu.byu.cs.tweeter.model.net.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;

public class FollowersCountRequest extends AuthorizedRequest {

    private String followeeAlias;

    private FollowersCountRequest() {super();}

    public FollowersCountRequest(AuthToken authToken, String currUserAlias, String followeeAlias) {
        super(authToken, currUserAlias);
        this.followeeAlias = followeeAlias;
    }

    public String getFolloweeAlias() {
        return followeeAlias;
    }

    public void setFolloweeAlias(String followeeAlias) {
        this.followeeAlias = followeeAlias;
    }
}
