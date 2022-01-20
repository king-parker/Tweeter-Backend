package edu.byu.cs.tweeter.model.net.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;

public class FollowRequest extends AuthorizedRequest {

    private String followerAlias;
    private String followeeAlias;

    private FollowRequest() {super();}

    public FollowRequest(AuthToken authToken, String currUserAlias, String followeeAlias) {
        super(authToken, currUserAlias);
        this.followerAlias = currUserAlias;
        this.followeeAlias = followeeAlias;
    }

    public FollowRequest(AuthToken authToken, String currUserAlias, String followerAlias, String followeeAlias) {
        super(authToken, currUserAlias);
        this.followerAlias = followerAlias;
        this.followeeAlias = followeeAlias;
    }

    public String getFollowerAlias() {
        return followerAlias;
    }

    public void setFollowerAlias(String followerAlias) {
        this.followerAlias = followerAlias;
    }

    public String getFolloweeAlias() {
        return followeeAlias;
    }

    public void setFolloweeAlias(String followeeAlias) {
        this.followeeAlias = followeeAlias;
    }

    @Override
    public String toString() {
        return "FollowRequest{" +
                "authToken=" + this.getAuthToken() +
                ", currUserAlias='" + this.getCurrUserAlias() + '\'' +
                ", followerAlias='" + followerAlias + '\'' +
                ", followeeAlias='" + followeeAlias + '\'' +
                '}';
    }
}
