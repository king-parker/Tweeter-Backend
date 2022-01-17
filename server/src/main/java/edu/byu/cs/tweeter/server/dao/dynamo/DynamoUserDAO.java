package edu.byu.cs.tweeter.server.dao.dynamo;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.server.dao.UserDAO;
import edu.byu.cs.tweeter.server.dao.dummy.DummyUserDAO;

public class DynamoUserDAO implements UserDAO {
    @Override
    public User getUser(String alias) {
        return new DummyUserDAO().getUser(alias);
    }

    @Override
    public User getLoginUser(String username, String password) {
        return new DummyUserDAO().getLoginUser(username, password);
    }

    @Override
    public User registerNewUser(String firstName, String lastName, String username, String password, String imageBytesBase64) {
        return new DummyUserDAO().registerNewUser(firstName, lastName, username, password, imageBytesBase64);
    }
}
