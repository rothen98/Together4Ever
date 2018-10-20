package model.server;

/**
 * An exception that should be thrown when no channels with
 * the given id could be find
 */
public class NoChannelFoundException extends Throwable {
     NoChannelFoundException() {
        super("No channel with the given id was found");
    }
}
