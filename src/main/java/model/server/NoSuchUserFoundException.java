package model.server;

/**
 * Throw this when no user has the given username
 */
public class NoSuchUserFoundException extends Exception {
     NoSuchUserFoundException() {
        super("There is no user with the given name");
    }
}
