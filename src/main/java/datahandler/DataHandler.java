package datahandler;

import model.chatcomponents.message.IMessage;
import model.chatcomponents.user.IUser;
import model.chatcomponents.user.User;
import model.chatcomponents.channel.IChannel;
import model.identifiers.IRecognizable;
import model.server.ChannelData;
import model.server.IDataHandler;
import model.server.MessageData;
import model.server.UserData;
import org.json.JSONArray;
import org.json.JSONObject;


import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class DataHandler implements IDataHandler {

    private FileWriter channelFile;
    private FileWriter userFile;
    private JSONArray readUserArray;
    private JSONArray readChannelArray;
    private JSONArray writeUserArray;
    private JSONArray writeChannelArray;


    public DataHandler() {

        readChannelArray = createJSONArrayFromFile("src/main/resources/channel.json");
        readUserArray = createJSONArrayFromFile("src/main/resources/user.json");

        writeChannelArray = new JSONArray();
        writeUserArray = new JSONArray();

        try {
            channelFile = new FileWriter("src/main/resources/channel.json");
            userFile = new FileWriter("src/main/resources/user.json");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private JSONArray createJSONArrayFromFile(String filename){
        try {
            String data = readFile(filename);
            if(!data.isEmpty()) {
                return new JSONArray(data);
            }else{
                return new JSONArray();
            }
        } catch (IOException e) {
            return new JSONArray();
        }

    }

    private String readFile(String filename) throws IOException {
        String result = "";

            BufferedReader br = new BufferedReader(new FileReader(filename));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }
            result = sb.toString();
            br.close();
        return result;
    }

    private void loopUsers(Collection<UserData> users) {
        for (UserData u : users) {
            writeUserArray.put(createNewUserJObject(u));
            }
    }

    private JSONObject createNewUserJObject(UserData u) {
        JSONObject user = new JSONObject();
        initUserJObject(u,user);
        return user;
    }
    private void initUserJObject(UserData u,JSONObject userJ){
        userJ.put("Username", u.getUsername());
        userJ.put("Password", u.getPassword());
        userJ.put("DisplayName", u.getDisplayName());
        userJ.put("DisplayImage", u.getDisplayImage());
    }


    private void loopChannels(Collection<ChannelData> channels) {
        for (ChannelData c : channels) {
                JSONObject channel = createNewChannelJObject(c);
                writeChannelArray.put(channel);
            }
    }


    private JSONObject createNewChannelJObject(ChannelData c) {
        JSONObject channel = new JSONObject();
        initChannelJObject(c, channel);
        return channel;
    }

    private void initChannelJObject(ChannelData c, JSONObject channel) {
        JSONArray message = new JSONArray();
        JSONArray user = new JSONArray();

        channel.put("ChannelName", c.getChannelName());
        channel.put("Description", c.getDescription());
        channel.put("DisplayImage", c.getImage());
        for(MessageData m: c.getMessages()) {
            JSONArray singleMessage = new JSONArray();
            singleMessage.put(m.getContent());
            singleMessage.put(m.getSenderName());
            singleMessage.put(m.getTimeStamp());
            singleMessage.put(m.getType());
            message.put(singleMessage);
        }
        channel.put("Messages", message);
        for (String u : c.getUserNames()) {
            user.put(u);
        }
        channel.put("Users", user);
    }


    public void pushUsers(Collection<UserData> users) {
        loopUsers(users);
        try {
            userFile.write(writeUserArray.toString(3));
            userFile.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        readUserArray  =writeUserArray;
        writeUserArray = new JSONArray();

    }

    public void pushChannels(Collection<ChannelData> channels) {
        loopChannels(channels);
        try {
            channelFile.write(writeChannelArray.toString(3));
            channelFile.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        readChannelArray=writeChannelArray;
        writeChannelArray=new JSONArray();

    }

    public Collection<UserData> getUsers() {
        Collection<UserData> users = new HashSet<>();
        for (int i = 0; i < readUserArray.length(); i++) {
            JSONObject jsonUser = readUserArray.getJSONObject(i);
            UserData user = new UserData(
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
        for (int i = 0; i < readChannelArray.length(); i++) {
            JSONObject jsonChannel = readChannelArray.getJSONObject(i);
            JSONArray temp = jsonChannel.getJSONArray("Messages");
            List<MessageData> messages = new ArrayList<>();

            for(int j = 0; j < temp.length(); j++) {
                JSONArray object = temp.getJSONArray(j);
                String content = (String)object.get(0) ;
                String senderName = (String)object.get(1);
                String timestamp = (String)object.get(2);
                String type = (String)object.get(3);

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
