package model.server;

import model.chatcomponents.message.MessageType;

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
