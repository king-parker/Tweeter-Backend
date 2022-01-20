package practice.follow;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.server.dao.dynamo.DynamoFollowDAO;
import edu.byu.cs.tweeter.server.util.Pair;

import java.util.Arrays;
import java.util.List;

public class GetFollowListsPractice {

    private static final int LIST_SIZE = 7;

    public static void main(String[] args) {

        List<String> aliases = Arrays.asList(
                "@eboshi", "@gonza", "@hii", "@jigo", "@kohroku", "@moro", "@nago", "@okkoto", "@san", "@toki", "@totoro"
        );

        DynamoFollowDAO dao = new DynamoFollowDAO();
        String user = "@ashitaka";
        int follows = dao.getFollowerCount(user);

        if (follows > 0) {
            System.err.println("Should start with 0 follows");
            return;
        }

        System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~~~~~ Start ~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.printf("%s has %d followers.%n", user, follows);

        System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~~~~~ Getting followed ~~~~~~~~~~~~~~~~~~~~~~~~~~");
        for (String alias : aliases) {
            dao.follow(alias, user);
        }

        follows = dao.getFollowerCount(user);
        System.out.printf("%s has %d followers.%n", user, follows);
        System.out.println("Displaying followers:");
        printFollowers(dao, user, follows > 0, null);

        System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~~~~~ Getting unfollowed ~~~~~~~~~~~~~~~~~~~~~~~~~~");
        for (String alias : aliases) {
            dao.unfollow(alias, user);
        }

        follows = dao.getFollowerCount(user);
        System.out.printf("%s has %d followers.%n", user, follows);
        System.out.println("Displaying followers:");
        printFollowers(dao, user, follows > 0, null);

        follows = dao.getFolloweeCount(user);
        System.out.printf("%s has followed %d users.%n", user, follows);
        System.out.println("Displaying followees:");
        printFollowees(dao, user, follows > 0, null);

        System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~~~~~ Following ~~~~~~~~~~~~~~~~~~~~~~~~~~");
        int decreaseSize = 3;
        for (int i = 0; i < aliases.size() - decreaseSize; i++) {
            dao.follow(user, aliases.get(i));
        }

        follows = dao.getFolloweeCount(user);
        System.out.printf("%s has followed %d users.%n", user, follows);
        System.out.println("Displaying followees:");
        printFollowees(dao, user, follows > 0, null);

        System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~~~~~ Unfollowing ~~~~~~~~~~~~~~~~~~~~~~~~~~");
        for (int i = 0; i < aliases.size() - decreaseSize; i++) {
            dao.unfollow(user, aliases.get(i));
        }

        follows = dao.getFolloweeCount(user);
        System.out.printf("%s has followed %d users.%n", user, follows);
        System.out.println("Displaying followees:");
        printFollowees(dao, user, follows > 0, null);
    }

    private static void printFollowers(DynamoFollowDAO dao, String followeeAlias, boolean hasMorePages, String lastFollower) {
        Pair<List<User>, Boolean> pair;
        if (hasMorePages) pair = dao.getFollowers(followeeAlias, LIST_SIZE, lastFollower);
        else return;

        System.out.println("Next section of the list");
        for (User user : pair.getFirst()) System.out.println(user);

        hasMorePages = pair.getSecond();
        lastFollower = pair.getFirst().get(pair.getFirst().size() - 1).getAlias();
        printFollowers(dao, followeeAlias, hasMorePages, lastFollower);
    }

    private static void printFollowees(DynamoFollowDAO dao, String followerAlias, boolean hasMorePages, String lastFollowee) {
        Pair<List<User>, Boolean> pair;
        if (hasMorePages) pair = dao.getFollowees(followerAlias, LIST_SIZE, lastFollowee);
        else return;

        System.out.println("Next section of the list");
        for (User user : pair.getFirst()) System.out.println(user);

        hasMorePages = pair.getSecond();
        lastFollowee = pair.getFirst().get(pair.getFirst().size() - 1).getAlias();
        printFollowees(dao, followerAlias, hasMorePages, lastFollowee);
    }
}
