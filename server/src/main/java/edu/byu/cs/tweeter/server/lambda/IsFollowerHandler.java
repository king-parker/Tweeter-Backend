package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import edu.byu.cs.tweeter.model.net.request.IsFollowingRequest;
import edu.byu.cs.tweeter.model.net.response.IsFollowingResponse;
import edu.byu.cs.tweeter.server.service.FollowService;

public class IsFollowerHandler implements RequestHandler<IsFollowingRequest, IsFollowingResponse> {
    @Override
    public IsFollowingResponse handleRequest(IsFollowingRequest request, Context context) {
        FollowService service = new FollowService();
        return service.isFollower(request);
    }
}
