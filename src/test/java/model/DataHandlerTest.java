package model;

import services.datahandler.DataHandler;
import model.chatcomponents.channel.Channel;
import model.chatcomponents.channel.IChannel;
import model.chatcomponents.message.MessageFactory;
import model.server.*;
import model.chatcomponents.user.IUser;
import model.chatcomponents.user.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.*;

public class DataHandlerTest {

    private DataHandler dataHandler = new DataHandler();


    IServer server;

    @Before
    public void setUp() throws Exception {
        server = new Server(dataHandler);
    }

    @After
    public void tearDown() throws Exception {
        server = null;
    }

    @Test
    public void loopUsers() {
        Collection<UserData> users = new HashSet<>();
        UserData user1 = new UserData("Spooodon", "Password", "London", "MyimageKat");
        UserData user2 = new UserData("MyName", "Password123", "JesusName", "MyimageDef");
        UserData user3 = new UserData("Tobbbbeeee", "Dont", "Rot", "MyimageTja");
        UserData user4 = new UserData("AlexS", "MyFuntime", "BigSol", "MyimageChooo");

        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);

        dataHandler.pushUsers(users);
        assertTrue(users.size() == 4);
        assertTrue(users.contains(user1));
    }

    @Test
    public void loopChannels() {
        Collection<ChannelData> channels = new HashSet<>();
        List<MessageData> messages = new ArrayList<>();
        List<String> users = new ArrayList<>();

        ChannelData channel1 = new ChannelData(
                "ChannelName",
                "FunDescription",
                "CoolImage",
                messages,
                users
        );
        ChannelData channel2 = new ChannelData(
                "ChannelName3223",
                "23232Description",
                "BorImage",
                messages,
                users
        );
        ChannelData channel3 = new ChannelData(
                "ChannelTob",
                "TobbbDescription",
                "TobbImage",
                messages,
                users
        );
        ChannelData channel4 = new ChannelData(
                "ChannelAS",
                "FunASASASAS",
                "Image.ASAS",
                messages,
                users
        );

        channels.add(channel1);
        channels.add(channel2);
        channels.add(channel3);
        channels.add(channel4);

        dataHandler.pushChannels(channels);

        assertTrue(channels.size() == 4);
    }

    @Test
    public void pushUser() {
        Collection<UserData> users = new HashSet<>();
        UserData user1 = new UserData("Spooodon", "Password", "London", "MyimageKat");
        UserData user2 = new UserData("MyName", "Password123", "JesusName", "MyimageDef");
        UserData user3 = new UserData("Tobbbbeeee", "Dont", "Rot", "MyimageTja");
        UserData user4 = new UserData("AlexS", "MyFuntime", "BigSol", "MyimageChooo");

        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);

        dataHandler.pushUsers(users);

        assertTrue(users.size() == 4);

    }

    @Test
    public void pushChannel() {
        Collection<ChannelData> channels = new HashSet<>();
        List<MessageData> messages = new ArrayList<>();
        List<String> users = new ArrayList<>();

        ChannelData channel1 = new ChannelData(
                "ChannelName",
                "FunDescription",
                "CoolImage",
                messages,
                users
        );
        ChannelData channel2 = new ChannelData(
                "ChannelName3223",
                "23232Description",
                "BorImage",
                messages,
                users
        );
        ChannelData channel3 = new ChannelData(
                "ChannelTob",
                "TobbbDescription",
                "TobbImage",
                messages,
                users
        );
        ChannelData channel4 = new ChannelData(
                "ChannelAS",
                "FunASASASAS",
                "Image.ASAS",
                messages,
                users
        );

        channels.add(channel1);
        channels.add(channel2);
        channels.add(channel3);
        channels.add(channel4);

        dataHandler.pushChannels(channels);
    }

    @Test
    public void getUsers() {
        Collection<UserData> users = new HashSet<>();

        UserData user1 = new UserData("Spooodon", "Password", "London", "MyimageKat");
        UserData user2 = new UserData("MyName", "Password123", "JesusName", "MyimageDef");
        UserData user3 = new UserData("Tobbbbeeee", "Dont", "Rot", "MyimageTja");
        UserData user4 = new UserData("AlexS", "MyFuntime", "BigSol", "MyimageChooo");

        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);

        dataHandler.pushUsers(users);

        Collection<UserData> catchUsers = dataHandler.getUsers();
        assertTrue(catchUsers.size() == 4);
    }

    @Test
    public void getChannels() {
        Collection<ChannelData> channels = new HashSet<>();
        IUser user = new User("USENERMAE", "gdfsgfd");
        MessageData message = new MessageData("This is my message", "myName", "myType", "2019");
        IChannel channel = new Channel("C12", "Cdes");
        channel.join(user);
        channel.sendMessage(MessageFactory.createTextMessage("ghjg",user));
        ChannelData channelData = new ChannelData(channel);


        channels.add(channelData);

        dataHandler.pushChannels(channels);

        Collection<ChannelData> testChannel = dataHandler.getChannels();
        assertTrue(testChannel.size() == 1);
    }
}