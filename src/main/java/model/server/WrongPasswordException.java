package model.server;

public class WrongPasswordException extends Exception {
    // Parameterless Constructor
    public WrongPasswordException() {
        super("Wrong password given");
    }

}
