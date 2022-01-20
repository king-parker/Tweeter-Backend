package edu.byu.cs.tweeter.model.net.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;

public class PostStatusRequest extends AuthorizedRequest {

    private Status status;

    private PostStatusRequest() {
        super();
    }

    public PostStatusRequest(AuthToken authToken, String currUserAlias, Status status) {
        super(authToken, currUserAlias);
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "PostStatusRequest{" +
                "authToken=" + this.getAuthToken() +
                ", currUserAlias='" + this.getCurrUserAlias() + '\'' +
                ", status=" + status +
                '}';
    }
}
