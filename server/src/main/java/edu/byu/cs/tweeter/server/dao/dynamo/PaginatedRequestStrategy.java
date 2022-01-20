package edu.byu.cs.tweeter.server.dao.dynamo;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.QueryRequest;
import com.amazonaws.services.dynamodbv2.model.QueryResult;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.DataAccessException;
import edu.byu.cs.tweeter.server.service.Service;
import edu.byu.cs.tweeter.server.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PaginatedRequestStrategy {

    public static QueryResult makeQuery(String tableName, String partitionKey, String partitionValue, String sortKey, String sortValue, int limit , String indexName) {
        String attrName = "#pKey";
        Map<String, String> attrNames = new HashMap<>();
        attrNames.put(attrName, partitionKey);

        String attrValue = ":pValue";
        Map<String, AttributeValue> attrValues = new HashMap<>();
        attrValues.put(attrValue, new AttributeValue().withS(partitionValue));

        QueryRequest queryRequest = new QueryRequest()
                .withTableName(tableName)
                .withKeyConditionExpression(attrName + " = " + attrValue)
                .withExpressionAttributeNames(attrNames)
                .withExpressionAttributeValues(attrValues)
                .withLimit(limit);

        if (isNonEmptyString(indexName)) queryRequest.withIndexName(indexName);

        if (isNonEmptyString(sortValue)) {
            Map<String, AttributeValue> startKey = new HashMap<>();
            startKey.put(partitionKey, new AttributeValue().withS(partitionValue));
            startKey.put(sortKey, new AttributeValue().withS(sortValue));

            queryRequest = queryRequest.withExclusiveStartKey(startKey);
        }

        QueryResult result;
        try {
            result = DynamoDAOFactory.getDbClient().query(queryRequest);
        } catch (Exception e) {
            String error = "Could not retrieve items from " + tableName;
            System.out.println(error);
            throw new DataAccessException(Service.SERVER_ERROR_TAG + " " + error, e.getCause());
        }

        return result;
    }

    public static <T> Pair<List<T>, Boolean> parseQueryResult(QueryResult queryResult, ItemMapper<T> mapper) {
        Pair<List<T>, Boolean> result = new Pair<>(null, null);
        List<Map<String, AttributeValue>> items = queryResult.getItems();

        if (items != null) {
            result.setFirst(new ArrayList<>());
            for (Map<String, AttributeValue> item : items){
                result.getFirst().add(mapper.mapItem(item));
            }
        }

        Map<String, AttributeValue> lastKey = queryResult.getLastEvaluatedKey();
        result.setSecond(lastKey != null);

        return result;
    }

    private static boolean isNonEmptyString(String value) {
        return (value != null && value.length() > 0);
    }

    public interface ItemMapper <T> {
        T mapItem(Map<String, AttributeValue> item);
    }

    public static class StatusExtractor {
        static Pair<List<Status>, Boolean> extractResults(QueryResult queryResult, String postKey, String firstnameKey, String lastnameKey, String posterAliasKey, String imageUrlKey, String timestampKey, String urlsKey, String mentionsKey) {
            return PaginatedRequestStrategy.parseQueryResult(queryResult, item -> {
                String post = item.get(postKey).getS();
                String firstname = item.get(firstnameKey).getS();
                String lastname = item.get(lastnameKey).getS();
                String alias = item.get(posterAliasKey).getS();
                String image = item.get(imageUrlKey).getS();
                String timestamp = item.get(timestampKey).getS();

                List<String> urls = new ArrayList<>();
                List<String> mentions = new ArrayList<>();
                if (item.containsKey(urlsKey))
                    urls = item.get(urlsKey).getSS();
                if (item.containsKey(mentionsKey))
                    mentions = item.get(mentionsKey).getSS();

                User user = new User(firstname, lastname, alias, image);
                return new Status(post, user, timestamp, urls, mentions);
            });
        }
    }

    public static class UserExtractor {
        static public Pair<List<User>, Boolean> extractResults(QueryResult queryResult, String firstnameKey, String lastnameKey, String aliasKey, String imageKey) {
            return PaginatedRequestStrategy.parseQueryResult(queryResult, item -> {
                String firstname = item.get(firstnameKey).getS();
                String lastname = item.get(lastnameKey).getS();
                String alias = item.get(aliasKey).getS();
                String image = item.get(imageKey).getS();
                return new User(firstname, lastname, alias, image);
            });
        }
    }
}
