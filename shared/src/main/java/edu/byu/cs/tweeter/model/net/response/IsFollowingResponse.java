package edu.byu.cs.tweeter.model.net.response;

public class IsFollowingResponse extends Response {

    private Boolean isFollower;

    public IsFollowingResponse(String message) {
        super(false, message);
    }

    public IsFollowingResponse(Boolean isFollower) {
        super(true);
        this.isFollower = isFollower;
    }

    public Boolean getIsFollower() {
        return isFollower;
    }

    @Override
    public String toString() {
        return "IsFollowingResponse{" +
                "success=" + this.isSuccess() +
                ", message='" + this.getMessage() + '\'' +
                ", isFollower=" + isFollower +
                '}';
    }
}
