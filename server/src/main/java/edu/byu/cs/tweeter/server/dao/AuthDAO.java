package edu.byu.cs.tweeter.server.dao;

import edu.byu.cs.tweeter.model.domain.AuthToken;

public class AuthDAO extends DAO {
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean isValidAuthToken(String alias, AuthToken authToken) {
        // TODO: Change to actual logic instead of dummy data
        return authToken != null && !alias.equals("@BadAuth");
    }

    public AuthToken generateLoginToken(String alias) {
        // TODO: Change to actual logic instead of dummy data
        return getDummyAuthToken();
    }

    public void endUserSession(AuthToken authToken) {
        // TODO: Change to actual logic
    }
}
