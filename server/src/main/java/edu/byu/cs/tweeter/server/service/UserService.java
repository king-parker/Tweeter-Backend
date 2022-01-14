package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.GetUserRequest;
import edu.byu.cs.tweeter.model.net.request.LoginRequest;
import edu.byu.cs.tweeter.model.net.response.GetUserResponse;
import edu.byu.cs.tweeter.model.net.response.LoginResponse;
import edu.byu.cs.tweeter.server.util.FakeData;

public class UserService extends Service {

    public LoginResponse login(LoginRequest request) {

        if (request.getUsername() == null) throw new RuntimeException(BAD_REQUEST_TAG + " No username");
        if (request.getPassword() == null) throw new RuntimeException(BAD_REQUEST_TAG + " No password");

        // TODO: Generates dummy data. Replace with a real implementation.
        User user = getUserDAO().getLoginUser(request.getUsername(), request.getPassword());

        if (user == null) throw new RuntimeException(BAD_REQUEST_TAG + " Username and password do not match");

        AuthToken authToken = getAuthDAO().generateLoginToken(request.getUsername());
        return new LoginResponse(user, authToken);
    }

    public GetUserResponse getUser(GetUserRequest request) {

        authenticateRequest(request);
        if (request.getUserAlias() == null) throw new RuntimeException(BAD_REQUEST_TAG + " No user designated to be retrieved");

        User user = getUserDAO().getUser(request.getUserAlias());
        if (user == null) throw new RuntimeException(BAD_REQUEST_TAG + " User does not exist");

        return new GetUserResponse(user);
    }
}
