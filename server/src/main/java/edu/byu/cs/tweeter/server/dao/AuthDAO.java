package edu.byu.cs.tweeter.server.dao;

import edu.byu.cs.tweeter.model.domain.AuthToken;

public class AuthDAO extends DAO {
    public boolean isValidAuthToken(String alias, AuthToken authToken) {
        // TODO: Change to actual logic instead of dummy data
        return !alias.equals("@BadAuth");
    }

    public AuthToken generateLoginToken(String alias) {
        // TODO: Change to actual logic instead of dummy data
        return getDummyAuthToken();
    }
}
