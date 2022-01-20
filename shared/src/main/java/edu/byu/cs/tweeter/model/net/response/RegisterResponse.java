package edu.byu.cs.tweeter.model.net.response;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class RegisterResponse extends Response {

    private User user;
    private AuthToken authToken;

    public RegisterResponse(String message) {
        super(false, message);
    }

    public RegisterResponse(User user, AuthToken authToken) {
        super(true);
        this.user = user;
        this.authToken = authToken;
    }

    public User getUser() {
        return user;
    }

    public AuthToken getAuthToken() {
        return authToken;
    }

    @Override
    public String toString() {
        return "RegisterResponse{" +
                "success=" + this.isSuccess() +
                ", message='" + this.getMessage() + '\'' +
                ", user=" + user +
                ", authToken=" + authToken +
                '}';
    }
}
