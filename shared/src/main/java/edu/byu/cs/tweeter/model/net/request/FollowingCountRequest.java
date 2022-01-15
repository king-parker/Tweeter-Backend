package edu.byu.cs.tweeter.model.net.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;

public class FollowingCountRequest extends AuthorizedRequest {

    private String followerAlias;

    private FollowingCountRequest() {super();}

    public FollowingCountRequest(AuthToken authToken, String currUserAlias, String followerAlias) {
        super(authToken, currUserAlias);
        this.followerAlias = followerAlias;
    }

    public String getFollowerAlias() {
        return followerAlias;
    }

    public void setFollowerAlias(String followerAlias) {
        this.followerAlias = followerAlias;
    }
}
