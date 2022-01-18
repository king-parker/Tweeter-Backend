package edu.byu.cs.tweeter.server.dao.dynamo;

import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.QueryResult;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.server.dao.FeedDAO;
import edu.byu.cs.tweeter.server.dao.dummy.DummyFeedDAO;
import edu.byu.cs.tweeter.server.util.Pair;

import java.util.List;

public class DynamoFeedDAO implements FeedDAO {
    private final String TABLE_NAME = "tweeter-feed";
    private final String PARTITION_KEY = "user_alias";
    private final String SORT_KEY = "datetime";
    private final String ATT_POST_KEY = "post";
    private final String ATT_URLS_KEY = "status_urls";
    private final String ATT_MEN_KEY = "status_mentions";
    private final String ATT_PAL_KEY = "poster_alias";
    private final String ATT_FN_KEY = "first_name";
    private final String ATT_LN_KEY = "last_name";
    private final String ATT_IMURL_NAME = "image_url";

    private final Table table;

    public DynamoFeedDAO() {
        table = DynamoDAOFactory.getDatabase().getTable(TABLE_NAME);
    }

    @Override
    public Integer getFeedCount(String userAlias) {
        // TODO: Do I need this?
        return new DummyFeedDAO().getFeedCount(userAlias);
    }

    @Override
    public Pair<List<Status>, Boolean> getFeed(String followerAlias, int limit, Status lastFeedStatus) {

        String lastTimestamp = (lastFeedStatus == null) ? null : lastFeedStatus.datetime;
        QueryResult queryResult = PaginatedRequestStrategy.makeQuery(TABLE_NAME, PARTITION_KEY, followerAlias,
                SORT_KEY, lastTimestamp, limit, null);

        return PaginatedRequestStrategy.parseQueryResult(queryResult, (DynamoFeedDAO.StatusMapper) item -> {
            String post = item.get(ATT_POST_KEY).getS();
            String firstname = item.get(ATT_FN_KEY).getS();
            String lastname = item.get(ATT_LN_KEY).getS();
            String alias = item.get(ATT_PAL_KEY).getS();
            String image = item.get(ATT_IMURL_NAME).getS();
            String timestamp = item.get(SORT_KEY).getS();
            List<String> urls = null;//item.get(ATT_URLS_KEY).getNS();
            List<String> mentions = null;//     item.get(ATT_MEN_KEY).getNS();
            User user = new User(firstname, lastname, alias, image);
            return new Status(post, user, timestamp, urls, mentions);
        });
//        return new DummyFeedDAO().getFeed(followerAlias, limit, lastFeedStatus);
    }

    @Override
    public void addStatus(String alias, Status followeeStatus) {

    }

    private interface StatusMapper extends PaginatedRequestStrategy.ItemMapper<Status> {}
}
