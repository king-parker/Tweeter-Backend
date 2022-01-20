package edu.byu.cs.tweeter.server.dao.dynamo;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.PutItemSpec;
import com.amazonaws.services.dynamodbv2.model.QueryResult;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.DataAccessException;
import edu.byu.cs.tweeter.server.dao.StoryDAO;
import edu.byu.cs.tweeter.server.dao.dummy.DummyStoryDAO;
import edu.byu.cs.tweeter.server.service.Service;
import edu.byu.cs.tweeter.server.util.Pair;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class DynamoStoryDAO implements StoryDAO {
    private final String LOG_TAG = "DYNAMO_STORY_DAO";

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
        System.out.printf("%s: %s is posting status: %s", LOG_TAG, status.getUserAlias(), status.getPost());
        // Put status in Story table
        Item item = new Item().withPrimaryKey(PARTITION_KEY, status.getUserAlias(), SORT_KEY, status.datetime)
                .withString(ATT_POST_KEY, status.getPost())
                .withString(ATT_FN_KEY, status.getUser().getFirstName())
                .withString(ATT_LN_KEY, status.getUser().getLastName())
                .withString(ATT_IMURL_NAME, status.getUser().getImageUrl());

        if (status.getUrls() != null && !status.getUrls().isEmpty()) {
            item.withStringSet(ATT_URLS_KEY, new HashSet<>(status.getUrls()));
        }
        if (status.getMentions() != null && !status.getMentions().isEmpty()) {
            item.withStringSet(ATT_MEN_KEY, new HashSet<>(status.getMentions()));
        }

        PutItemSpec spec = new PutItemSpec().withItem(item);

        try {
            PutItemOutcome outcome = table.putItem(spec);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
            throw new DataAccessException(String.format("%s Error adding status to %s's story", Service.SERVER_ERROR_TAG, status.getUserAlias()) , e.getCause());
        }

        DynamoFollowDAO followDAO = new DynamoFollowDAO();
        DynamoFeedDAO feedDAO = new DynamoFeedDAO();
        @SuppressWarnings("UnusedAssignment") Pair<List<User>, Boolean> follows = new Pair<>(null, null);
        String lastFollowerAlias = null;
        boolean hasMorePages = true;

        while (hasMorePages) {
            follows = populateFeedTable(status, status.getUserAlias(), lastFollowerAlias, followDAO, feedDAO);

            List<User> users = follows.getFirst();
            User lastUser = users.get(users.size() - 1);
            lastFollowerAlias = lastUser.getAlias();

            hasMorePages = follows.getSecond();
        }
    }

    private Pair<List<User>, Boolean> populateFeedTable(Status status, String posterAlias, String lastFollowerAlias, DynamoFollowDAO followDAO, DynamoFeedDAO feedDAO) {
        int batchAmount = 25;
        Pair<List<User>, Boolean> follows = followDAO.getFollowers(posterAlias, batchAmount, lastFollowerAlias);

        for (User user : follows.getFirst()) feedDAO.addStatus(user.getAlias(), status);

        return follows;
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
