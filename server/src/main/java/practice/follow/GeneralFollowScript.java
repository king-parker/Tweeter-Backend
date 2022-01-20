package practice.follow;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.server.dao.dynamo.DynamoFollowDAO;
import edu.byu.cs.tweeter.server.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GeneralFollowScript {

    public static void main(String[] args) {
        List<String> aliases = Arrays.asList(
                "@eboshi", "@gonza", "@hii", "@jigo", "@kohroku", "@moro", "@nago", "@okkoto", "@san", "@toki", "@totoro"
        );

        DynamoFollowDAO dao = new DynamoFollowDAO();
        List<Pair<String, List<String>>> result = new ArrayList<>();

        int range = 4;
        for (int i = 0; i < aliases.size(); i++) {
            result.add(new Pair<>(aliases.get(i),new ArrayList<>()));
            for (int j = (i - range); j < (i + range + 1); j++) {
                if (i == j) continue;

                int index;
                if (j < 0) index = aliases.size() + j;
                else if (j >= aliases.size()) index = j - aliases.size();
                else index = j;

                if (!dao.isFollower(aliases.get(i), aliases.get(index))) {
                    dao.follow(aliases.get(i), aliases.get(index));
                }
            }
            Pair<List<User>, Boolean> list = dao.getFollowees(aliases.get(i), 10, null);
            for (User user : list.getFirst()) {
                result.get(i).getSecond().add(user.getAlias());
            }
        }

        System.out.println();
        for (Pair<String, List<String>> pair : result) {
            System.out.println(pair.getFirst());
            for (String follow : pair.getSecond()) {
                System.out.println("\t" + follow);
            }
            System.out.println();
        }
    }
}
