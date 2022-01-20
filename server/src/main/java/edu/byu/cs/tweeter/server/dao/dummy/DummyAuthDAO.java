package edu.byu.cs.tweeter.server.dao.dummy;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.server.dao.AuthDAO;

public class DummyAuthDAO extends DummyDAO implements AuthDAO {
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    @Override
    public boolean isValidAuthToken(String alias, AuthToken authToken) {
        // Change to actual logic instead of dummy data
        return authToken != null && !alias.equals("@BadAuth");
    }

    @Override
    public AuthToken generateLoginToken(String alias) {
        // Change to actual logic instead of dummy data
        return getDummyAuthToken();
    }

    @Override
    public void endUserSession(String alias, AuthToken authToken) {
        // Change to actual logic
    }
}
