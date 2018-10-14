package model.interaction.message;

public class ChannelContent implements IMessageContent {

    private final String channelMessage;

    public ChannelContent(String channelMessage) {
        this.channelMessage = channelMessage;
    }

    @Override
    public MessageType getType() {
        return MessageType.CHANNEL;
    }

    @Override
    public String getMessage() {
        return channelMessage;
    }
}
