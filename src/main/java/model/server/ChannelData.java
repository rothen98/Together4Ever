package model.server;

import java.util.List;

public class ChannelData {
    private String description;
    private String image;
    private List<MessageData> messages;
    private List<String> userNames;
    private String channelName;

    public ChannelData(String name, String description, String image, List<MessageData> messages, List<String> usernames) {
        this.channelName = name;
        this.description = description;
        this.image = image;
        this.messages = messages;
        this.userNames = usernames;
    }

    public List<String> getUserNames() {
        return userNames;
    }

    public String getChannelName() {
        return channelName;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public List<MessageData> getMessages() {
        return messages;
    }
}
