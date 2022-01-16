package edu.byu.cs.tweeter.server.dao;

import edu.byu.cs.tweeter.model.domain.AuthToken;

public interface AuthDAO {
    boolean isValidAuthToken(String alias, AuthToken authToken);
    AuthToken generateLoginToken(String alias);
    void endUserSession(String alias, AuthToken authToken);
}
