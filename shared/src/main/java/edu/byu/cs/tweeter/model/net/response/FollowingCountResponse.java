package edu.byu.cs.tweeter.model.net.response;

public class FollowingCountResponse extends Response {

    private Integer count;

    public FollowingCountResponse(String message) {
        super(false, message);
    }

    public FollowingCountResponse(Integer count) {
        super(true);
        this.count = count;
    }

    public Integer getCount() {
        return count;
    }
}
