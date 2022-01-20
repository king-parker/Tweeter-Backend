package practice.auth;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.server.dao.dynamo.DynamoAuthDAO;

public class TokenPractice {

    public static void main(String[] args) throws InterruptedException {
        class MockDAO extends DynamoAuthDAO {
            public MockDAO() {
                super();
            }

            @Override
            public AuthToken getToken(String alias, String datetime) {
                return super.getToken(alias, datetime);
            }
        }

         MockDAO dao = new MockDAO();

        String alias = "@ashitaka";
        AuthToken firstToken = dao.generateLoginToken(alias);
        System.out.printf("Generated auth token: %s%n", firstToken.toString());

        for (int i = 1; i < 10; i++) {
            System.out.printf("%d...", i);
            Thread.sleep(1000);
        }

        AuthToken secondToken;
        if (dao.isValidAuthToken(alias, firstToken)) {
            secondToken = dao.getToken(firstToken.getUserAlias(), firstToken.getDatetime());
            System.out.printf("Recent activity: %s%n", secondToken.toString());
        }
        else {
            throw new RuntimeException("Could not get token after creating it.");
        }

        System.out.printf("End result...%nInitial: %s%nUpdated: %s%n", secondToken.getDatetime(), secondToken.getLastActivity());
    }
}
