package practice.status;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.util.Timestamp;
import edu.byu.cs.tweeter.server.dao.dynamo.DynamoFeedDAO;

import java.util.ArrayList;
import java.util.Collections;

public class PutFeedPractice {

    public static void main(String[] args) {
        String follower ="@ashitaka";
        User user = new User("Princess", "Mononoke", "@san", "https://pk-tweeter-profile-images.s3.us-west-2.amazonaws.com/san");
        Status status = new Status("@eboshi stinks for killing wolves. You should visit https://earthjustice.org/.",
                user,
                Timestamp.getDisplayString(Timestamp.getDatetime()),
                new ArrayList<>(Collections.singleton("https://earthjustice.org/")),
                new ArrayList<>(Collections.singleton("@eboshi")));

        DynamoFeedDAO dao = new DynamoFeedDAO();
        dao.addStatus(follower, status);
    }

//    public static List<String> parseURLs(String post) {
//        List<String> containedUrls = new ArrayList<>();
//        for (String word : post.split("\\s")) {
//            if (word.startsWith("http://") || word.startsWith("https://")) {
//
//                int index = findUrlEndIndex(word);
//
//                word = word.substring(0, index);
//
//                containedUrls.add(word);
//            }
//        }
//
//        return containedUrls;
//    }
//
//    public static List<String> parseMentions(String post) {
//        List<String> containedMentions = new ArrayList<>();
//
//        for (String word : post.split("\\s")) {
//            if (word.startsWith("@")) {
//                word = word.replaceAll("[^a-zA-Z0-9]", "");
//                word = "@".concat(word);
//
//                containedMentions.add(word);
//            }
//        }
//
//        return containedMentions;
//    }
//
//    public static int findUrlEndIndex(String word) {
//        if (word.contains(".com")) {
//            int index = word.indexOf(".com");
//            index += 4;
//            return index;
//        } else if (word.contains(".org")) {
//            int index = word.indexOf(".org");
//            index += 4;
//            return index;
//        } else if (word.contains(".edu")) {
//            int index = word.indexOf(".edu");
//            index += 4;
//            return index;
//        } else if (word.contains(".net")) {
//            int index = word.indexOf(".net");
//            index += 4;
//            return index;
//        } else if (word.contains(".mil")) {
//            int index = word.indexOf(".mil");
//            index += 4;
//            return index;
//        } else {
//            return word.length();
//        }
//    }
}
