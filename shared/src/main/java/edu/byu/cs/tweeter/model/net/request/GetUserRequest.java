package edu.byu.cs.tweeter.model.net.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;

public class GetUserRequest extends AuthorizedRequest {

    private String userAlias;

    private GetUserRequest() {super();}

    public GetUserRequest(AuthToken authToken, String currUserAlias, String userAlias) {
        super(authToken, currUserAlias);
        this.userAlias = userAlias;
    }

    public String getUserAlias() {
        return userAlias;
    }

    public void setUserAlias(String userAlias) {
        this.userAlias = userAlias;
    }

    @Override
    public String toString() {
        return "GetUserRequest{" +
                "authToken=" + this.getAuthToken() +
                ", currUserAlias='" + this.getCurrUserAlias() + '\'' +
                ", userAlias='" + userAlias + '\'' +
                '}';
    }
}
