package practice.users;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.DataAccessException;
import edu.byu.cs.tweeter.server.dao.dynamo.DynamoUserDAO;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

public class UserPutPractice {

    public static void main(String[] args) throws IOException, DataAccessException {
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

        for (User addUser : users) {
            String name = addUser.getAlias().replaceFirst("@", "");

            String filename = String.format("server/src/main/java/practice/s3/%s.jpg", name);
            byte[] imageData = Files.readAllBytes(new File(filename).toPath());
            String encodedBase64Image = Base64.getEncoder().encodeToString(imageData);

            User registeredUser = new DynamoUserDAO().registerNewUser(addUser.getFirstName(), addUser.getLastName(), addUser.getAlias(), "password", encodedBase64Image);
            if (registeredUser == null) {
                System.err.printf("Could not add user %s to the database%n", addUser.getAlias());
            }
            else {
                assert registeredUser == addUser;
                System.out.printf("Added user %s to the database%n", addUser.getAlias());
            }
        }
    }
}
