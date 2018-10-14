package model.interaction.message;

public class ImageContent implements IMessageContent {

    private final String imageMessage;

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
