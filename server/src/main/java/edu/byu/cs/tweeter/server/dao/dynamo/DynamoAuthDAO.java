package edu.byu.cs.tweeter.server.dao.dynamo;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.server.dao.AuthDAO;
import edu.byu.cs.tweeter.server.dao.dummy.DummyAuthDAO;

public class DynamoAuthDAO implements AuthDAO {
    @Override
    public boolean isValidAuthToken(String alias, AuthToken authToken) {
        return new DummyAuthDAO().isValidAuthToken(alias, authToken);
    }

    @Override
    public AuthToken generateLoginToken(String alias) {
        return new DummyAuthDAO().generateLoginToken(alias);
    }

    @Override
    public void endUserSession(String alias, AuthToken authToken) {

    }
}
