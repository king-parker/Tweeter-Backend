package practice.status;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.util.Timestamp;
import edu.byu.cs.tweeter.server.dao.dynamo.DynamoStoryDAO;

import java.util.Arrays;

public class PostStatusPractice {

    public static void main(String[] args) {
        String follower ="@ashitaka";
        User user = new User("Lord", "Ashitaka", "@ashitaka", "https://pk-tweeter-profile-images.s3.us-west-2.amazonaws.com/ashitaka");
        Status status = new Status("@eboshi and @san, I'm sure we can figure out a solution.",
                user,
                Timestamp.getDisplayString(Timestamp.getDatetime()),
                null,
                Arrays.asList("@eboshi", "@san"));

        DynamoStoryDAO dao = new DynamoStoryDAO();
        dao.postStatus(status);
    }
}
