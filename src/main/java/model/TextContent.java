package model;

public class TextContent implements IMessageContent {

    private final String textMessage;

    public TextContent(String textMessage) {
        this.textMessage = textMessage;
    }
    @Override
    public MessageType getType() {
        return MessageType.TEXT;
    }

    @Override
    public String getMessage() {
        return textMessage;
    }
}
