package edu.byu.cs.tweeter.model.net.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;

public class AuthorizedRequest extends Request {

    private AuthToken authToken;
    private String currUserAlias;

    protected AuthorizedRequest() {super();}

    public AuthorizedRequest(AuthToken authToken, String currUserAlias) {
        this.authToken = authToken;
        this.currUserAlias = currUserAlias;
    }

    public AuthToken getAuthToken() {
        return authToken;
    }

    public void setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
    }

    public String getCurrUserAlias() {
        return currUserAlias;
    }

    public void setCurrUserAlias(String currUserAlias) {
        this.currUserAlias = currUserAlias;
    }
}
