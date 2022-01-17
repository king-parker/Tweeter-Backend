package practice.s3;

import edu.byu.cs.tweeter.server.dao.dynamo.S3DAO;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

public class S3Practice {

    public static void main(String[] args) throws IOException {

        List<String> images = Arrays.asList(
                "ashitaka", "eboshi", "gonza", "hii", "jigo", "kohroku", "moro", "nago", "okkoto", "san", "toki"
        );

        for (String name : images) {
            String filename = String.format("server/src/main/java/practice/s3/%s.jpg", name);
            System.out.println(filename);
            byte[] imageData = Files.readAllBytes(new File(filename).toPath());

            String alias = String.format("@%s", name);
            System.out.println(alias);
            String encodedBase64Image = Base64.getEncoder().encodeToString(imageData);
            S3DAO s3DAO = new S3DAO();
            String uri = s3DAO.upload(alias, encodedBase64Image);

            System.out.println(String.format("Successfully uploaded the image: %s", uri));
            System.out.println();
        }
    }
}
