package practice.status;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.server.dao.dynamo.DynamoFeedDAO;
import edu.byu.cs.tweeter.server.util.Pair;

import java.util.List;

public class GetFeedPractice {

    public static void main(String[] args) {
        Pair<List<Status>, Boolean> result = new DynamoFeedDAO().getFeed("@ashitaka", 10, null);
        for (Status status : result.getFirst()) System.out.println(status);
    }
}
