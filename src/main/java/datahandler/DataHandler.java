package datahandler;

import model.chatcomponents.message.IMessage;
import model.chatcomponents.user.IUser;
import model.chatcomponents.user.User;
import model.chatcomponents.channel.IChannel;
import model.identifiers.IRecognizable;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;

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
                JSONArray timestamp = new JSONArray();
                message.put(m.getMessage());
                message.put(m.getSender().getDisplayName());
                timestamp.put(m.getTimestamp().getYear());
                timestamp.put(m.getTimestamp().getMonthValue());
                timestamp.put(m.getTimestamp().getDayOfMonth());
                timestamp.put(m.getTimestamp().getHour());
                timestamp.put(m.getTimestamp().getMinute());
                message.put(timestamp);
                message.put(m.getType());
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

    public Collection<IChannel> getChannels() {
        Collection<IChannel> channels = new HashSet<>();
        for (int i = 0; i < channelArray.length(); i++) {
            JSONObject jsonChannel = userArray.getJSONObject(i);
            /*IChannel channel = new Channel(
                    jsonChannel.get("ChannelName").toString(),
                    jsonChannel.get("Description").toString(),
                    jsonChannel.get("DisplayImage").toString()
                    //This isnt done yet. Finsih tonight
            );*/
        }

        return channels;
    }
}
