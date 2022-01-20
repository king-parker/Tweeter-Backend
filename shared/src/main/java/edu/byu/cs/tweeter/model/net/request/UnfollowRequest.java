package edu.byu.cs.tweeter.model.net.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;

public class UnfollowRequest extends AuthorizedRequest {

    private String followerAlias;
    private String unfolloweeAlias;

    private UnfollowRequest() {super();}

    public UnfollowRequest(AuthToken authToken, String currUserAlias, String unfolloweeAlias) {
        super(authToken, currUserAlias);
        this.followerAlias = currUserAlias;
        this.unfolloweeAlias = unfolloweeAlias;
    }

    public UnfollowRequest(AuthToken authToken, String currUserAlias, String followerAlias, String unfolloweeAlias) {
        super(authToken, currUserAlias);
        this.followerAlias = followerAlias;
        this.unfolloweeAlias = unfolloweeAlias;
    }

    public String getFollowerAlias() {
        return followerAlias;
    }

    public void setFollowerAlias(String followerAlias) {
        this.followerAlias = followerAlias;
    }

    public String getUnfolloweeAlias() {
        return unfolloweeAlias;
    }

    public void setUnfolloweeAlias(String unfolloweeAlias) {
        this.unfolloweeAlias = unfolloweeAlias;
    }

    @Override
    public String toString() {
        return "UnfollowRequest{" +
                "authToken=" + this.getAuthToken() +
                ", currUserAlias='" + this.getCurrUserAlias() + '\'' +
                ", followerAlias='" + followerAlias + '\'' +
                ", unfolloweeAlias='" + unfolloweeAlias + '\'' +
                '}';
    }
}
