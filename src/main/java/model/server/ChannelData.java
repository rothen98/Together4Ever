package model.server;

import model.chatcomponents.channel.IChannel;
import model.chatcomponents.message.IMessage;
import model.identifiers.IRecognizable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class contains all the channel data that is stored at the end of a session and fetched at the beginning.
 * All contents of the channel, including messages and users are stored
 * The class is a middle ground for server to use
 *
 * @author Alex Solberg
 */
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

    public ChannelData(IChannel channel) {
        this.channelName =channel.getDisplayName();
        this.description =channel.getDescription();
        this.image = channel.getDisplayImage();
        this.messages = new ArrayList<>();
        for(IMessage message:channel.getAllMessages()){
            messages.add(new MessageData(message));
        }
        this.userNames=new ArrayList<>();
        for(IRecognizable i:channel.getAllUsersInfo()){
            userNames.add(i.getDisplayName());
        }
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
