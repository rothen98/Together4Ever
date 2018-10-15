package datahandler;

import model.chatcomponents.message.IMessage;
import model.chatcomponents.user.IUser;
import model.chatcomponents.user.User;
import model.chatcomponents.channel.IChannel;
import model.identifiers.IRecognizable;
import model.server.ChannelData;
import model.server.IDataHandler;
import model.server.MessageData;
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
        try {
            String data = readFile("src/main/resources/channel.json");
            if(!data.isEmpty()) {
                readChannelArray = new JSONArray(data);
            }else{
                readChannelArray = new JSONArray();
            }
        } catch (IOException e) {
            readChannelArray = new JSONArray();
        }

        try {
            String data = readFile("src/main/resources/user.json");
            if(!data.isEmpty()) {
                readUserArray = new JSONArray(data);
            }else{
                readUserArray = new JSONArray();
            }
        } catch (IOException e) {
            readUserArray = new JSONArray();
        }

        writeChannelArray = new JSONArray();
        writeUserArray = new JSONArray();



        try {
            channelFile = new FileWriter("src/main/resources/channel.json");
            userFile = new FileWriter("src/main/resources/user.json");
        } catch (IOException e) {
            e.printStackTrace();
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

    public void loopUsers(Collection<IUser> users) {
        for (IUser u : users) {
            writeUserArray.put(createNewUserJObject(u));
            }
    }



    private void updateUser(IUser u) {
        JSONObject user;
        for(int i = 0; i< readUserArray.length(); i++){
            if(readUserArray.getJSONObject(i).get("Username").equals(u.getName())){
                user = readChannelArray.getJSONObject(i);
                initUserJObject(u,user);
            }
        }
    }

    private JSONObject createNewUserJObject(IUser u) {
        JSONObject user = new JSONObject();
        initUserJObject(u,user);
        return user;
    }
    private void initUserJObject(IUser u,JSONObject userJ){
        userJ.put("Username", u.getName());
        userJ.put("Password", u.getHashedPassword());
        userJ.put("DisplayName", u.getDisplayName());
        userJ.put("DisplayImage", u.getDisplayImage());
    }

    private boolean userExistsInJson(IUser u) {
        for(int i = 0; i< readUserArray.length(); i++){
            if(readUserArray.getJSONObject(i).get("Username").equals(u.getName())){
                return true;
            }
        }
        return false;
    }

    private void loopChannels(Collection<IChannel> channels) {
        for (IChannel c : channels) {
                JSONObject channel = createNewChannelJObject(c);
                writeChannelArray.put(channel);
            }
    }

    private void updateChannel(IChannel c) {
        JSONObject channel;
        for(int i = 0; i< readChannelArray.length(); i++){
            if(readChannelArray.getJSONObject(i).get("ChannelName").equals(c.getDisplayName())){
                channel = readChannelArray.getJSONObject(i);
                initChannelJObject(c,channel);
            }
        }

    }

    private JSONObject createNewChannelJObject(IChannel c) {
        JSONObject channel = new JSONObject();
        initChannelJObject(c, channel);
        return channel;
    }

    private void initChannelJObject(IChannel c, JSONObject channel) {
        JSONArray message = new JSONArray();
        JSONArray user = new JSONArray();

        channel.put("ChannelName", c.getDisplayName());
        channel.put("Description", c.getDescription());
        channel.put("DisplayImage", c.getDisplayImage());
        for(IMessage m: c.getAllMessages()) {
            JSONArray singleMessage = new JSONArray();
            singleMessage.put(m.getMessage());
            singleMessage.put(m.getSender().getDisplayName());
            singleMessage.put(m.getTimestamp().toString());
            singleMessage.put(m.getType());
            message.put(singleMessage);
        }
        channel.put("Messages", message);
        for (IRecognizable u : c.getAllUsers()) {
            user.put(u.getDisplayName());
        }
        channel.put("Users", user);
    }

    private boolean channelExistInJson(IChannel c) {
        for(int i = 0; i< readChannelArray.length(); i++){
            if(readChannelArray.getJSONObject(i).get("ChannelName").equals(c.getDisplayName())){
                return true;
            }
        }
        return false;
    }

    public void pushUsers(Collection<IUser> users) {
        loopUsers(users);
        try {
            userFile.write(writeUserArray.toString());
            userFile.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void pushChannels(Collection<IChannel> channels) {
        System.out.println("Saving channels");
        loopChannels(channels);
        try {
            channelFile.write(writeChannelArray.toString());
            channelFile.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Collection<IUser> getUsers() {
        Collection<IUser> users = new HashSet<>();
        for (int i = 0; i < readUserArray.length(); i++) {
            JSONObject jsonUser = readUserArray.getJSONObject(i);
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
        System.out.println("Getting Channels...");
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
                String type = (String) object.get(3);
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
