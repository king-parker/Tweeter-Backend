package edu.byu.cs.tweeter.server.dao;

import edu.byu.cs.tweeter.model.domain.User;

public interface UserDAO {
    User getUser(String alias);
    User getLoginUser(String username, String password);
    User registerNewUser(String firstName, String lastName, String username, String password, String imageBytesBase64);
}
