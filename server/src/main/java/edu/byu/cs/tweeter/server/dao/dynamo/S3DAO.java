package edu.byu.cs.tweeter.server.dao.dynamo;

import com.amazonaws.SdkClientException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import edu.byu.cs.tweeter.model.net.DataAccessException;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.server.service.Service;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.Base64;

public class S3DAO {

    private static final String BUCKET_NAME = "pk-tweeter-profile-images";

    public String upload(String alias, String encodedBase64Image) throws DataAccessException {

        URL url;
        String filename = alias;
        try {
            AmazonS3 s3 = AmazonS3ClientBuilder
                    .standard()
                    .withRegion(Regions.US_WEST_2)
                    .build();

            if (filename.charAt(0) == '@') {
                filename = filename.replaceFirst("@","");
            }

            byte[] imageBytes = Base64.getDecoder().decode(encodedBase64Image);

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(imageBytes.length);
            metadata.setContentType("image/jpeg");

            PutObjectRequest fileRequest = new PutObjectRequest(BUCKET_NAME, filename, new ByteArrayInputStream(imageBytes), metadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead);

            s3.putObject(fileRequest);

            url = s3.getUrl(BUCKET_NAME, filename);
        } catch (SdkClientException e) {
            System.out.println(e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
            throw new DataAccessException(Service.SERVER_ERROR_TAG + " Error uploading picture", e.getCause());
        }

        return url.toString();
    }
}
