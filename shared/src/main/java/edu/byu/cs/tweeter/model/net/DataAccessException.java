package edu.byu.cs.tweeter.model.net;

import java.util.List;

public class DataAccessException extends Exception {
    public DataAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}
