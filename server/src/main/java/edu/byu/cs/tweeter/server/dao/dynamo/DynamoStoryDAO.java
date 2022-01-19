package edu.byu.cs.tweeter.server.dao.dynamo;

import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.QueryResult;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.server.dao.StoryDAO;
import edu.byu.cs.tweeter.server.dao.dummy.DummyStoryDAO;
import edu.byu.cs.tweeter.server.util.Pair;

import java.util.List;

public class DynamoStoryDAO implements StoryDAO {
    private final String TABLE_NAME = "tweeter-story";
    private final String PARTITION_KEY = "user_alias";
    private final String SORT_KEY = "datetime";
    private final String ATT_POST_KEY = "post";
    private final String ATT_URLS_KEY = "status_urls";
    private final String ATT_MEN_KEY = "status_mentions";
    private final String ATT_FN_KEY = "first_name";
    private final String ATT_LN_KEY = "last_name";
    private final String ATT_IMURL_NAME = "image_url";

    private final Table table;

    public DynamoStoryDAO() {
        table = DynamoDAOFactory.getDatabase().getTable(TABLE_NAME);
    }

    @Override
    public void postStatus(Status status) {
    }

    @Override
    public int getStoryCount(String userAlias) {
        return new DummyStoryDAO().getStoryCount(userAlias);
    }

    @Override
    public Pair<List<Status>, Boolean> getStory(String followerAlias, int limit, Status lastStoryStatus) {

        String lastTimestamp = (lastStoryStatus == null) ? null : lastStoryStatus.datetime;
        QueryResult queryResult = PaginatedRequestStrategy.makeQuery(TABLE_NAME, PARTITION_KEY, followerAlias,
                SORT_KEY, lastTimestamp, limit, null);

        return PaginatedRequestStrategy.StatusExtractor.extractResults(queryResult, ATT_POST_KEY, ATT_FN_KEY, ATT_LN_KEY, PARTITION_KEY, ATT_IMURL_NAME, SORT_KEY, ATT_URLS_KEY, ATT_MEN_KEY);
    }
}
