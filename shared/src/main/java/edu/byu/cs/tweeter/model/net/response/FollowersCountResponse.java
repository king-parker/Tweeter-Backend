package edu.byu.cs.tweeter.model.net.response;

public class FollowersCountResponse extends Response {

    private Integer count;

    public FollowersCountResponse(String message) {
        super(false, message);
    }

    public FollowersCountResponse(Integer count) {
        super(true);
        this.count = count;
    }

    public Integer getCount() {
        return count;
    }
}
