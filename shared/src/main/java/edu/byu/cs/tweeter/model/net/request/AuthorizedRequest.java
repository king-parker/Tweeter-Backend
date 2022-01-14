package edu.byu.cs.tweeter.model.net.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;

public class AuthorizedRequest {

    private AuthToken authToken;
    private String currUserAlias;

    protected AuthorizedRequest() { }

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
