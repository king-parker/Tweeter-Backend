package edu.byu.cs.tweeter.server.dao;

import edu.byu.cs.tweeter.model.domain.User;

public class UserDAO extends DAO {
    public User getUser(String alias) {
        // TODO: Generates dummy data. Replace with a real implementation.
        return getDummyUser();
    }

    public User getLoginUser(String username, String password) {
        // TODO: Generates dummy data. Replace with a real implementation.
        if (username.equals("@BadLogin")) return null;
        else return getDummyUser();
    }
}
