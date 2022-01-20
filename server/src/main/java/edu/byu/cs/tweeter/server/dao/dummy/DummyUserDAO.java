package edu.byu.cs.tweeter.server.dao.dummy;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.server.dao.UserDAO;

public class DummyUserDAO extends DummyDAO implements UserDAO {
    @Override
    public User getUser(String alias) {
        // Generates dummy data. Replace with a real implementation.
        return getFakeData().findUserByAlias(alias);
    }

    @Override
    public User getLoginUser(String username, String password) {
        // Generates dummy data. Replace with a real implementation.
        if (username.equals("@BadLogin")) return null;
        else return getDummyUser();
    }

    @Override
    public User registerNewUser(String firstName, String lastName, String username, String password, String imageBytesBase64) {
        // Generates dummy data. Replace with a real implementation.
        //  Also, the image string needs to get decoded somewhere
        if (username.equals("@BadLogin")) return null;
        else return getDummyUser();
    }
}
