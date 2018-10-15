package datahandler;

import model.chatcomponents.channel.Channel;
import model.chatcomponents.message.IMessage;
import model.chatcomponents.user.IUser;
import model.chatcomponents.user.User;
import model.chatcomponents.channel.IChannel;
import model.identifiers.IRecognizable;
import model.server.ChannelData;
import model.server.MessageData;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class DataHandler {

    private FileWriter channelFile;
    private FileWriter userFile;
    private JSONArray userArray;
    private JSONArray channelArray;

    public DataHandler() {
         userArray = new JSONArray();
         channelArray = new JSONArray();

        try {
            channelFile = new FileWriter("src/main/resources/channel.json");
            userFile = new FileWriter("src/main/resources/user.json");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void loopUsers(Collection<IUser> users) {
        for (IUser u : users) {
            JSONObject user = new JSONObject();

            user.put("Username", u.getName());
            user.put("Password", u.getHashedPassword());
            user.put("DisplayName", u.getDisplayName());
            user.put("DisplayImage", u.getDisplayImage());

            userArray.put(user);
        }
    }

    public void loopChannels(Collection<IChannel> channels) {
        for (IChannel c : channels) {
            JSONObject channel = new JSONObject();
            JSONArray message = new JSONArray();
            JSONArray user = new JSONArray();

            channel.put("ChannelName", c.getDisplayName());
            channel.put("Description", c.getDescription());
            channel.put("DisplayImage", c.getDisplayImage());
            for(IMessage m: c.getAllMessages()) {
                JSONArray singleMessage = new JSONArray();
                JSONArray timestamp = new JSONArray();
                singleMessage.put(m.getMessage());
                singleMessage.put(m.getSender().getDisplayName());
                timestamp.put(m.getTimestamp().getYear());
                timestamp.put(m.getTimestamp().getMonthValue());
                timestamp.put(m.getTimestamp().getDayOfMonth());
                timestamp.put(m.getTimestamp().getHour());
                timestamp.put(m.getTimestamp().getMinute());
                singleMessage.put(timestamp);
                singleMessage.put(m.getType());
                message.put(singleMessage);
            }
            channel.put("Messages", message);
            for (IRecognizable u : c.getAllUsers()) {
                user.put(u.getDisplayName());
            }
            channel.put("Users", user);

            channelArray.put(channel);
        }

    }

    public void pushUser(Collection<IUser> users) {
        loopUsers(users);
        try {
            userFile.write(userArray.toString());
            userFile.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void pushChannel(Collection<IChannel> channels) {
        loopChannels(channels);
        try {
            channelFile.write(channelArray.toString());
            channelFile.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Collection<IUser> getUsers() {
        Collection<IUser> users = new HashSet<>();
        for (int i = 0; i < userArray.length(); i++) {
            JSONObject jsonUser = userArray.getJSONObject(i);
            IUser user = new User(
                    jsonUser.get("Username").toString(),
                    jsonUser.get("Password").toString(),
                    jsonUser.get("DisplayName").toString(),
                    jsonUser.get("DisplayImage").toString()
            );
            users.add(user);
        }
        return users;
    }

    public Collection<ChannelData> getChannels() {
        Collection<ChannelData> channels = new HashSet<>();
        for (int i = 0; i < channelArray.length(); i++) {
            JSONObject jsonChannel = channelArray.getJSONObject(i);
            JSONArray temp = jsonChannel.getJSONArray("Message");
            List<MessageData> messages = new ArrayList<>();

            for(int j = 0; j < temp.length(); j++) {
                String content = (String)temp.get(0);
                String senderName = (String)temp.get(1);
                Integer[] timestamp = (Integer[])temp.get(2);
                String type = (String)temp.get(3);
                MessageData data = new MessageData(content,senderName,type,timestamp);
                messages.add(data);
            }

            List<String> users = new ArrayList<>();
            JSONArray jUsers = jsonChannel.getJSONArray("Users");
            for (Object o :jUsers) {
                users.add((String)o);
            }

            ChannelData channel = new ChannelData(
                    jsonChannel.get("ChannelName").toString(),
                    jsonChannel.get("Description").toString(),
                    jsonChannel.get("DisplayImage").toString(),
                    messages,
                    users
            );
            channels.add(channel);
        }
        return channels;
    }
}
