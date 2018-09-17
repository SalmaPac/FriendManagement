package api.service.friendmanagement.Util;

/**
 * Custom Data Exception class to return user friendly messages
 */
public class DataException extends RuntimeException{

    public DataException(String message) {
        super(message);
    }
}
