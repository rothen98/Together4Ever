package model.server;

/**
 * Throw this exception when a request for a certain user fails because of
 * an incorrect password.
 */
public class WrongPasswordException extends Exception {

   WrongPasswordException() {
        super("Wrong password given");
    }

}
