package edu.byu.cs.tweeter.server.lambda.config;

import edu.byu.cs.tweeter.server.dao.DAOFactory;
import edu.byu.cs.tweeter.server.dao.dynamo.DynamoDAOFactory;

public class HandlerConfig {

    private static HandlerConfig config;

    public static HandlerConfig getInstance() {
        if (config == null) {
            config = new HandlerConfig();
        }

        return config;
    }

    private HandlerConfig() {}

    public DAOFactory getFactory() {
        return new DynamoDAOFactory();
    }
}
