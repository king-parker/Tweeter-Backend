package edu.byu.cs.tweeter.server.dao.dynamo;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.QueryRequest;
import com.amazonaws.services.dynamodbv2.model.QueryResult;
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

        return DynamoDAOFactory.getDbClient().query(queryRequest);
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
}
