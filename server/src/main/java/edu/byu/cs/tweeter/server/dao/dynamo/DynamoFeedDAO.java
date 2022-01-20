package edu.byu.cs.tweeter.server.dao.dynamo;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.PutItemSpec;
import com.amazonaws.services.dynamodbv2.model.QueryResult;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.net.DataAccessException;
import edu.byu.cs.tweeter.server.dao.FeedDAO;
import edu.byu.cs.tweeter.server.dao.dummy.DummyFeedDAO;
import edu.byu.cs.tweeter.server.service.Service;
import edu.byu.cs.tweeter.server.util.Pair;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class DynamoFeedDAO implements FeedDAO {
    private final String LOG_TAG = "DYNAMO_FEED_DAO";
    
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
    public Pair<List<Status>, Boolean> getFeed(String followeeStatusAlias, int limit, Status lastFeedStatus) {

        String lastTimestamp = (lastFeedStatus == null) ? null : lastFeedStatus.datetime;
        QueryResult queryResult = PaginatedRequestStrategy.makeQuery(TABLE_NAME, PARTITION_KEY, followeeStatusAlias,
                SORT_KEY, lastTimestamp, limit, null);

        System.out.printf("%s:Retrieving feed for %s%n", LOG_TAG, followeeStatusAlias);
        return PaginatedRequestStrategy.StatusExtractor.extractResults(queryResult, ATT_POST_KEY, ATT_FN_KEY,
                ATT_LN_KEY, ATT_PAL_KEY, ATT_IMURL_NAME, SORT_KEY, ATT_URLS_KEY, ATT_MEN_KEY);
    }

    @Override
    public void addStatus(String alias, Status followeeStatus) {
        Item item = new Item().withPrimaryKey(PARTITION_KEY, alias, SORT_KEY, followeeStatus.datetime)
                .withString(ATT_POST_KEY, followeeStatus.getPost())
                .withString(ATT_FN_KEY, followeeStatus.getUser().getFirstName())
                .withString(ATT_LN_KEY, followeeStatus.getUser().getLastName())
                .withString(ATT_PAL_KEY, followeeStatus.getUser().getAlias())
                .withString(ATT_IMURL_NAME, followeeStatus.getUser().getImageUrl());
        if (followeeStatus.getUrls() != null) item.withStringSet(ATT_URLS_KEY, new HashSet<>(followeeStatus.getUrls()));
        if (followeeStatus.getMentions() != null) item.withStringSet(ATT_MEN_KEY, new HashSet<>(followeeStatus.getMentions()));
        PutItemSpec spec = new PutItemSpec().withItem(item);

        try {
            PutItemOutcome outcome = table.putItem(spec);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
            throw new DataAccessException(String.format("%s Error adding status to %s's feed%n", Service.SERVER_ERROR_TAG, alias) , e.getCause());
        }
        System.out.printf("%s:%s had status posted to their feed by %s: %s%n",
                LOG_TAG, followeeStatus.getUserAlias(), alias, followeeStatus.getPost());
    }
}
