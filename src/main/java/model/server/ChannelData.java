package model.server;

import java.util.List;

public class ChannelData {
    private String name;
    private String description;
    private String image;
    private List<MessageData> messages;
    private List<String> usernames;

    public ChannelData(String name, String description, String image, List<MessageData> messages, List<String> usernames) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.messages = messages;
        this.usernames = usernames;
    }
}
