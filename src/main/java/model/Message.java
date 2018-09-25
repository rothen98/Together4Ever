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
    private IMessageContent messageContent;
    private LocalDateTime timestamp;


    public Message(IUser sender, IMessageContent messageContent, LocalDateTime timestamp) {

        this.sender = sender;
        this.messageContent = messageContent;
        this.timestamp = timestamp;
    }

    /**
     *
     * @return the (text) message that was written
     */
    @Override
    public IMessageContent getMessageContent() {
        return messageContent;
    }

    /**
     *
     * @return the timestamp in a specific format
     */
    @Override
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    /**
     *
     * @return the person who wrote the message
     */
    @Override
    public IUser getSender() {
        return sender;
    }

}
