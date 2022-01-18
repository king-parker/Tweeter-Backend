package practice.users;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.server.dao.dynamo.DynamoUserDAO;

import java.io.File;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

public class UserGetPractice {

    public static void main(String[] args) {
        List<User> users = Arrays.asList(
                new User("Lord", "Ashitaka", "@ashitaka", "https://pk-tweeter-profile-images.s3.us-west-2.amazonaws.com/ashitaka"),
                new User("Princess", "Mononoke", "@san", "https://pk-tweeter-profile-images.s3.us-west-2.amazonaws.com/san"),
                new User("Lady", "Eboshi", "@eboshi", "https://pk-tweeter-profile-images.s3.us-west-2.amazonaws.com/eboshi"),
                new User("Jigo", "the Godhunter", "@jigo", "https://pk-tweeter-profile-images.s3.us-west-2.amazonaws.com/jigo"),
                new User("Gonza", "the Bodygaurd", "@gonza", "https://pk-tweeter-profile-images.s3.us-west-2.amazonaws.com/gonza"),
                new User("Moro", "the Wolf God", "@moro", "https://pk-tweeter-profile-images.s3.us-west-2.amazonaws.com/moro"),
                new User("Okkoto", "the Boar God", "@okkoto", "https://pk-tweeter-profile-images.s3.us-west-2.amazonaws.com/okkoto"),
                new User("Nago", "Demon Boar", "@nago", "https://pk-tweeter-profile-images.s3.us-west-2.amazonaws.com/nago"),
                new User("Kohroku", "Cattle Driver", "@kohroku", "https://pk-tweeter-profile-images.s3.us-west-2.amazonaws.com/kohroku"),
                new User("Toki", "Irontown Influencer", "@toki", "https://pk-tweeter-profile-images.s3.us-west-2.amazonaws.com/toki"),
                new User("Hii", "sama", "@hii", "https://pk-tweeter-profile-images.s3.us-west-2.amazonaws.com/hii")
        );

        for (User getUser : users) {
            User retrievedUser = new DynamoUserDAO().getUser(getUser.getAlias());

            assert retrievedUser == getUser;
        }
    }
}
