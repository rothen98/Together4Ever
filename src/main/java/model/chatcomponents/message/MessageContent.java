package model.chatcomponents.message;

public class MessageContent implements IMessageContent {

    private final String message;
    private final MessageType msgType;


    public MessageContent(String content, MessageType msgType){
        this.message = content;
        this.msgType = msgType;
    }

    @Override
    public MessageType getType() {
        return msgType;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
