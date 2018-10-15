package model.server;

public class NoChannelFoundException extends Throwable {
    public NoChannelFoundException() {
        super("No channel with the given id was found");
    }
}
