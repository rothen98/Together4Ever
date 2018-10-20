package model.server;

import model.chatcomponents.message.IMessage;

/**
 * This class contains all the message data that is stored at the end of a session and fetched at the beginning.
 * The data is stored in the channel file as the messages exist in a channel
 * The class is a middle ground for server to use
 * @author Alex Solberg
 */
public class MessageData {
    private String sendername;
    private String content;
    private String type;
    private String timestamp;


    public MessageData( String content,String sendername, String type, String timestamp) {
        this.sendername = sendername;
        this.content = content;
        this.type = type;
        this.timestamp = timestamp;
    }

    public MessageData(IMessage message) {
        this.sendername = message.getSenderName();
        this.content = message.getMessage();
        this.type = message.getType().toString();
        this.timestamp = message.getTimestamp().toString();
    }


    public String getContent() {
        return content;
    }

    public String getType() {
        return type;
    }

    public String getSenderName() {
        return sendername;
    }

    public String getTimeStamp() {
        return timestamp;
    }
}
