package edu.byu.cs.tweeter.server.dao.dynamo;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.UpdateItemOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.PutItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.net.DataAccessException;
import edu.byu.cs.tweeter.model.util.Timestamp;
import edu.byu.cs.tweeter.server.dao.AuthDAO;
import edu.byu.cs.tweeter.server.service.Service;

import java.util.Arrays;

public class DynamoAuthDAO implements AuthDAO {
    private final String LOG_TAG = "DYNAMO_AUTH_DAO";

    private final String TABLE_NAME = "tweeter-auth_token";
    private final String PARTITION_KEY = "user_alias";
    private final String SORT_KEY = "datetime";
    private final String ATT_TOKEN_NAME = "auth_token";
    private final String ATT_LUS_NAME = "last_use";
    private final String ATT_ACTIVE_NAME = "is_active";

    private final Table table;

    public DynamoAuthDAO() {
        table = DynamoDAOFactory.getDatabase().getTable(TABLE_NAME);
    }

    @Override
    public boolean isValidAuthToken(String alias, AuthToken authToken) {
        AuthToken foundToken = getToken(authToken.getUserAlias(), authToken.getDatetime());

        if (!authToken.getToken().equals(foundToken.getToken())) return false;
        if (!foundToken.getIsValid()) return false;

        String currTime = Timestamp.getDatetime();
        if (!Timestamp.isWithinRange(foundToken.getDatetime(), currTime, TOKEN_TIMEOUT)) return false;

        System.out.printf("%s: Token is still valid%n", LOG_TAG);

        // Update recent activity time
        String activity = ":activity";

        System.out.printf("%s: Updating the recent activity timestamp%n", LOG_TAG);
        UpdateItemSpec updateSpec = new UpdateItemSpec()
                .withPrimaryKey(PARTITION_KEY, alias, SORT_KEY, authToken.getDatetime())
                .withUpdateExpression("set " + ATT_LUS_NAME + "=" + activity)
                .withValueMap(new ValueMap().withString(activity, currTime))
                .withConditionExpression("attribute_exists(" + PARTITION_KEY + ")");

        try {
            UpdateItemOutcome updateOutcome = table.updateItem(updateSpec);
        } catch (Exception e) {
            String error = "Could not update token activity";
            System.out.printf("%s: %s%n", LOG_TAG, error);
            System.out.println(e.getMessage());
            throw new DataAccessException(Service.SERVER_ERROR_TAG + " " + error);
        }

        return true;
    }

    protected AuthToken getToken(String alias, String datetime) {
        System.out.printf("%s: Getting token for %s%n", LOG_TAG, alias);

        GetItemSpec getSpec = new GetItemSpec().withPrimaryKey(PARTITION_KEY, alias, SORT_KEY, datetime);
        AuthToken foundToken;

        Item outcome = table.getItem(getSpec);

        if (outcome != null) {
            foundToken = new AuthToken(outcome.getString(PARTITION_KEY), outcome.getString(ATT_TOKEN_NAME),
                    outcome.getString(SORT_KEY), outcome.getBoolean(ATT_ACTIVE_NAME), outcome.getString(ATT_LUS_NAME));
            System.out.printf("%s: Retrieved token:%n", LOG_TAG);
            System.out.println("\t" + foundToken);
        }
        else {
            String error = "Could not retrieve token";
            System.out.printf("%s: %s%n", LOG_TAG, error);
            throw new DataAccessException(Service.SERVER_ERROR_TAG + " " + error);
        }

        return foundToken;
    }

    @Override
    public AuthToken generateLoginToken(String alias) {
        System.out.printf("%s: Generating a new auth token%n", LOG_TAG);

        AuthToken authToken = new AuthToken(alias, AuthToken.generateToken(), Timestamp.getDatetime(), true);

        Item item = new Item().withPrimaryKey(PARTITION_KEY, authToken.getUserAlias(), SORT_KEY, authToken.getDatetime())
                .withString(ATT_TOKEN_NAME, authToken.getToken())
                .withString(ATT_LUS_NAME, authToken.getDatetime())
                .withBoolean(ATT_ACTIVE_NAME, true);
        PutItemSpec spec = new PutItemSpec().withItem(item);

        System.out.printf("%s: Attempting to add auth token to database%n", LOG_TAG);
        try {
            PutItemOutcome outcome = table.putItem(spec);
        } catch (Exception e) {
            System.out.printf("%s: %s%n", LOG_TAG, e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
            throw new DataAccessException(Service.SERVER_ERROR_TAG + " Error creating auth token", e.getCause());
        }

        System.out.printf("%s: Token created for: %s%n", LOG_TAG, alias);
        return authToken;
    }

    @Override
    public void endUserSession(String alias, AuthToken authToken) {
        System.out.printf("%s: Revoking auth token%n", LOG_TAG);

        String valid = ":valid";
        UpdateItemSpec updateSpec = new UpdateItemSpec()
                .withPrimaryKey(PARTITION_KEY, alias, SORT_KEY, authToken.getDatetime())
                .withUpdateExpression("set " + ATT_ACTIVE_NAME + "=" + valid)
                .withValueMap(new ValueMap().withBoolean(valid, false))
                .withConditionExpression("attribute_exists(" + PARTITION_KEY + ")");

        try {
            UpdateItemOutcome updateOutcome = table.updateItem(updateSpec);
        } catch (Exception e) {
            String error = "Could not update token";
            System.out.printf("%s: %s%n", LOG_TAG, error);
            System.out.println(e.getMessage());
            throw new DataAccessException(Service.SERVER_ERROR_TAG + " " + error);
        }
    }
}
