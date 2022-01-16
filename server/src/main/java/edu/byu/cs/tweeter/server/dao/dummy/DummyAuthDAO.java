package edu.byu.cs.tweeter.server.dao.dummy;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.server.dao.AuthDAO;

public class DummyAuthDAO extends DummyDAO implements AuthDAO {
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    @Override
    public boolean isValidAuthToken(String alias, AuthToken authToken) {
        // TODO: Change to actual logic instead of dummy data
        return authToken != null && !alias.equals("@BadAuth");
    }

    @Override
    public AuthToken generateLoginToken(String alias) {
        // TODO: Change to actual logic instead of dummy data
        return getDummyAuthToken();
    }

    @Override
    public void endUserSession(AuthToken authToken) {
        // TODO: Change to actual logic
    }
}
