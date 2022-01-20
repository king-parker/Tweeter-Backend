package practice.users;

import edu.byu.cs.tweeter.server.dao.dynamo.DynamoUserDAO;

public class PasswordPractice {

    public static void main(String[] args) {
        class MockDAO extends DynamoUserDAO {
            MockDAO() {super();}

            @Override
            protected String hashPassword(String passwordToHash) {
                return super.hashPassword(passwordToHash);
            }
        }

        MockDAO dao = new MockDAO();

        for (int i = 0; i < 10; i++) {
            System.out.println(dao.hashPassword("password"));
        }
    }
}
