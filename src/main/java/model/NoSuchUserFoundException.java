package model;

public class NoSuchUserFoundException extends Exception {
    public NoSuchUserFoundException() {
        super("There is no user with the given name");
    }
}
