package edu.byu.cs.tweeter.model.net.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;

public class LogoutRequest extends AuthorizedRequest {

    private LogoutRequest() {super();}

    public LogoutRequest(AuthToken authToken, String currUserAlias) {
        super(authToken, currUserAlias);
    }

    @Override
    public String toString() {
        return "LogoutRequest{" +
                "authToken=" + this.getAuthToken() +
                ", currUserAlias='" + this.getCurrUserAlias() + '\'' +
                '}';
    }
}
