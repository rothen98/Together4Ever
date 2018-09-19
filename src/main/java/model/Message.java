package model;

import java.time.LocalDateTime;

public class Message implements IMessage {

    private IUser sender;
    private String message;
    private LocalDateTime timestamp;


    public Message(IUser sender, String message, LocalDateTime timestamp) {

        this.sender = sender;
        this.message = message;
        this.timestamp = timestamp;


    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public IUser getSender() {
        return sender;
    }

}
