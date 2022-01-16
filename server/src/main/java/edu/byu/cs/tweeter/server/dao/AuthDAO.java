package edu.byu.cs.tweeter.server.dao;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.util.Timestamp;

public interface AuthDAO {
    long TOKEN_TIMEOUT = Timestamp.getDuration(0,10, 0);

    boolean isValidAuthToken(String alias, AuthToken authToken);
    AuthToken generateLoginToken(String alias);
    void endUserSession(String alias, AuthToken authToken);
}
