package model.server;

import model.chatcomponents.message.MessageType;

public class MessageData {
    private String sendername;
    private String content;
    private MessageType type;
    private Integer[] timestamp;


    public MessageData(String sendername, String content, MessageType type, Integer[] timestamp) {
        this.sendername = sendername;
        this.content = content;
        this.type = type;
        this.timestamp = timestamp;
    }


}
