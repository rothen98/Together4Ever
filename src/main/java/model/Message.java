package model;

import java.time.LocalDateTime;

/**
 * @author Alex Solberg
 *
 * This class contains data about the messages which exist in a channel
 * The objects contain a user, a (test)message and a timestamp
 */

public class Message implements IMessage {

    private IUser sender;
    private String message;
    private LocalDateTime timestamp;


    public Message(IUser sender, String message, LocalDateTime timestamp) {

        this.sender = sender;
        this.message = message;
        this.timestamp = timestamp;
    }

    /**
     *
     * @return the (text) message that was written
     */
    public String getMessage() {
        return message;
    }

    /**
     *
     * @return the timestamp in a specific format
     */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    /**
     *
     * @return the person who wrote the message
     */
    public IUser getSender() {
        return sender;
    }

}
