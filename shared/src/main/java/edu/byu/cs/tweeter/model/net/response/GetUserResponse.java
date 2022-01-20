package edu.byu.cs.tweeter.model.net.response;

import edu.byu.cs.tweeter.model.domain.User;

public class GetUserResponse extends Response {

    private User user;

    public GetUserResponse(String message) {
        super(false, message);
    }

    public GetUserResponse(User user) {
        super(true);
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "GetUserResponse{" +
                "success=" + this.isSuccess() +
                ", message='" + this.getMessage() + '\'' +
                ", user=" + user +
                '}';
    }
}
