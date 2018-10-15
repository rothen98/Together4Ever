package model.chatcomponents.message;

import model.identifiers.IRecognizable;
import model.chatcomponents.user.IUser;

import java.time.LocalDateTime;

/**
 * @author Alex Solberg
 *
 * This class contains data about the messages which exist in a channel
 * The chatcomponents contain a user, a (test)message and a timestamp
 */

public class Message implements IMessage {

    private final IUser sender;
    private final IMessageContent messageContent;
    private final LocalDateTime timestamp;


     public Message(IUser sender, IMessageContent messageContent) {

        this.sender = sender;
        this.messageContent = messageContent;
        this.timestamp = LocalDateTime.now();
    }
    public Message(IUser sender, IMessageContent messageContent, LocalDateTime timestamp){
         this.sender = sender;
         this.messageContent = messageContent;
         this.timestamp = timestamp;
    }

    /**
     *
     * @return the (text) message that was written
     */
    /*@Override
    public IMessageContent getMessageContent() {
        return messageContent;
    }*/

    @Override
    public MessageType getType(){
        return messageContent.getType();
    }

    @Override
    public String getMessage(){
        return messageContent.getMessage();
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
    public IRecognizable getSender() {
        return sender;
    }

}
