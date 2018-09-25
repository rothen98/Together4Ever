package model;

public class ImageContent implements IMessageContent {

    private String imageMessage;

    public ImageContent(String imageMessage) {
        this.imageMessage = imageMessage;
    }

    @Override
    public MessageType getType() {
        return MessageType.IMAGE;
    }

    @Override
    public String getMessage() {
        return imageMessage;
    }
}