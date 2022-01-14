package edu.byu.cs.tweeter.model.net.response;

import edu.byu.cs.tweeter.model.domain.Status;

import java.util.List;

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
