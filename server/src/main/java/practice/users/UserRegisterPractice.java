package practice.users;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.server.dao.dynamo.DynamoUserDAO;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

public class UserRegisterPractice {

    public static void main(String[] args) throws IOException {

        String firstName = "My Neighbor";
        String lastName = "Totoro";
        String handle = "totoro";

        String filename = String.format("server/src/main/java/practice/users/%s.jpg", handle);
        byte[] imageData = Files.readAllBytes(new File(filename).toPath());

        String alias = String.format("@%s", handle);
        String encodedBase64Image = Base64.getEncoder().encodeToString(imageData);

        User registeredUser = new DynamoUserDAO().registerNewUser(firstName, lastName, alias, "password", encodedBase64Image);

        System.out.println(registeredUser.getImageUrl());
    }
}
