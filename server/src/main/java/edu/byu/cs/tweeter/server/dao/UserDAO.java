package edu.byu.cs.tweeter.server.dao;

import edu.byu.cs.tweeter.model.domain.User;

public class UserDAO extends DAO {
    public User getUser(String alias) {
        // TODO: Generates dummy data. Replace with a real implementation.
        return getFakeData().findUserByAlias(alias);
    }

    public User getLoginUser(String username, String password) {
        // TODO: Generates dummy data. Replace with a real implementation.
        if (username.equals("@BadLogin")) return null;
        else return getDummyUser();
    }

    public User registerNewUser(String firstName, String lastName, String username, String password, String imageBytesBase64) {
        // TODO: Generates dummy data. Replace with a real implementation.
        //  Also, the image string needs to get decoded somewhere
        if (username.equals("@BadLogin")) return null;
        else return getDummyUser();
    }
}
