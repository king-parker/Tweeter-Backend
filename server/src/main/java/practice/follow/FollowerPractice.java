package practice.follow;

import edu.byu.cs.tweeter.server.dao.dynamo.DynamoFollowDAO;

public class FollowerPractice {

    public static void main(String[] args) {
        DynamoFollowDAO dao = new DynamoFollowDAO();
        String followerAlias = "@ashitaka";
        String followeeAlias = "@san";

        assert !dao.isFollower(followerAlias, followeeAlias);
        assert dao.getFollowerCount(followerAlias) == 0;
        assert dao.getFollowerCount(followeeAlias) == 0;

//        dao.follow(followerAlias, followeeAlias);
//
//        assert dao.isFollower(followerAlias, followeeAlias);
//        assert dao.getFollowerCount(followerAlias) == 1;
//        assert dao.getFollowerCount(followeeAlias) == 1;

        dao.unfollow(followerAlias, followeeAlias);

        assert !dao.isFollower(followerAlias, followeeAlias);
        assert dao.getFollowerCount(followerAlias) == 0;
        assert dao.getFollowerCount(followeeAlias) == 0;
    }
}
