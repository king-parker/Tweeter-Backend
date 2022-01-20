package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import edu.byu.cs.tweeter.model.net.request.FollowerRequest;
import edu.byu.cs.tweeter.model.net.response.FollowerResponse;
import edu.byu.cs.tweeter.server.service.FollowService;
import edu.byu.cs.tweeter.server.service.Service;

import java.util.Arrays;

public class GetFollowersHandler implements RequestHandler<FollowerRequest, FollowerResponse> {
    private final String LOG_TAG = "GET_FOLLOWERS_HANDLER";

    @Override
    public FollowerResponse handleRequest(FollowerRequest request, Context context) {
        System.out.printf("Request has been sent to %s%n", LOG_TAG);
        FollowService service;
        try {
            service = new FollowService();
        }
        catch (Exception e) {
            System.out.printf("%s: An error occurred - %s%n", LOG_TAG, e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
            throw new RuntimeException(String.format("%s Could not start service", Service.SERVER_ERROR_TAG));
        }
        System.out.printf("%s: Starting getFollower task%n", LOG_TAG);
        return service.getFollowers(request);
    }
}
