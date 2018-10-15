package model;

import datahandler.DataHandler;
import model.chatcomponents.channel.Channel;
import model.chatcomponents.channel.IChannel;
import model.chatcomponents.message.IMessage;
import model.chatcomponents.message.IMessageContent;
import model.chatcomponents.message.Message;
import model.chatcomponents.message.MessageFactory;
import model.server.ChannelData;
import model.server.IServer;
import model.server.Server;
import model.chatcomponents.user.IUser;
import model.chatcomponents.user.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import static org.junit.Assert.*;

public class DataHandlerTest {

    private DataHandler dataHandler = new DataHandler();


    IServer server;

    @Before
    public void setUp() throws Exception {
        server = new Server();
    }

    @After
    public void tearDown() throws Exception {
        server = null;
    }

    @Test
    public void loopUsers() {
        Collection<IUser> users = new HashSet<>();
        IUser user1 = new User("Text", "test");
        IUser user2 = new User("Text23", "test2");
        IUser user3 = new User("Textew", "test3");
        users.add(user1);
        users.add(user2);
        users.add(user3);
        dataHandler.loopUsers(users);
        assertTrue(users.size() == 3);
        assertTrue(users.contains(user1));
    }

    @Test
    public void loopChannels() {
    }

    @Test
    public void pushUser() {
        Collection<IUser> users = new HashSet<>();

        IUser user1 = new User("Text", "test");
        IUser user2 = new User("Text23", "test2");
        IUser user3 = new User("Textew", "test3");
        IUser user4 = new User("Text231", "test4");
        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);

        dataHandler.pushUser(users);

        assertTrue(users.size() == 4);
    }

    @Test
    public void pushChannel() {
        Collection<IChannel> channels = new HashSet<>();
        IUser user = new User("USENERMAE", "gdfsgfd");
        IMessage message = MessageFactory.createTextMessage("This is my message",user);

        IChannel c1 = new Channel("ChannelName1", "ChannelDesicrgsghds");
        c1.sendMessage(message);
        c1.join(user);
        IChannel c2 = new Channel("C12", "Cdes");
        IChannel c3 = new Channel("CHKFS#", "test");
        IChannel c4 = new Channel("CName5", "test");
        channels.add(c1);
        channels.add(c2);
        channels.add(c3);
        channels.add(c4);

        dataHandler.pushChannel(channels);
    }

    @Test
    public void getUsers() {
        Collection<IUser> users = new HashSet<>();

        IUser user1 = new User("Text", "test");
        IUser user2 = new User("Text23", "test2");
        IUser user3 = new User("Textew", "test3");
        IUser user4 = new User("Text231", "test4");
        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);

        dataHandler.pushUser(users);

        Collection<IUser> catchUsers = dataHandler.getUsers();
        assertTrue(catchUsers.size() == 4);
        System.out.println(catchUsers);
    }

    @Test
    public void getChannels() {
        Collection<IChannel> channels = new HashSet<>();
        IUser user = new User("USENERMAE", "gdfsgfd");
        IMessage message = MessageFactory.createTextMessage("This is my message", user);

        IChannel c1 = new Channel("ChannelName1", "ChannelDesicrgsghds");
        c1.sendMessage(message);
        c1.join(user);
        IChannel c2 = new Channel("C12", "Cdes");
        IChannel c3 = new Channel("CHKFS#", "test");
        IChannel c4 = new Channel("CName5", "test");
        channels.add(c1);
        channels.add(c2);
        channels.add(c3);
        channels.add(c4);

        dataHandler.pushChannel(channels);

        Collection<ChannelData> shittyShit = dataHandler.getChannels();
        assertTrue(shittyShit.size() == 4);
    }
}