package practice.users;

import edu.byu.cs.tweeter.server.dao.dynamo.DynamoUserDAO;

public class FollowerCountPractice {

    public static void main(String[] args) {

        DynamoUserDAO dao = new DynamoUserDAO();
        String alias = "@ashitaka";

        System.out.printf("  Initial follow count: %s\n", dao.getFollowerCount(alias));
        System.out.printf("Initial followee count: %s\n", dao.getFolloweeCount(alias));
        System.out.println();

        dao.changeFollowCount(alias, true);
        dao.changeFolloweeCount(alias, false);

        System.out.printf("  Second follow count: %s\n", dao.getFollowerCount(alias));
        System.out.printf("Second followee count: %s\n", dao.getFolloweeCount(alias));
        System.out.println();

        dao.changeFollowCount(alias, false);
        dao.changeFolloweeCount(alias, true);

        System.out.printf("  Final follow count: %s\n", dao.getFollowerCount(alias));
        System.out.printf("Final followee count: %s\n", dao.getFolloweeCount(alias));
        System.out.println();
    }
}
