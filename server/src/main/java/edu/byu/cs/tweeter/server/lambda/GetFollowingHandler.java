package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import edu.byu.cs.tweeter.model.net.request.FollowingRequest;
import edu.byu.cs.tweeter.model.net.response.FollowingResponse;
import edu.byu.cs.tweeter.server.service.FollowService;

public class GetFollowingHandler implements RequestHandler<FollowingRequest, FollowingResponse> {
    private final String LOG_TAG = "GET_FOLLOWERS_HANDLER";

    @Override
    public FollowingResponse handleRequest(FollowingRequest request, Context context) {
        System.out.printf("Request has been sent to %s%n", LOG_TAG);
        FollowService service = new FollowService();
        System.out.printf("%s: Starting getFollowing task%n", LOG_TAG);
        return service.getFollowees(request);
    }
}
