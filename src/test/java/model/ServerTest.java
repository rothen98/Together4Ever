package model;

import org.junit.BeforeClass;
import services.PasswordEncryption.JBCryptAdapter;
import services.datahandler.DataHandler;
import model.server.*;
import model.chatcomponents.user.IUser;
import model.chatcomponents.user.User;
import model.chatcomponents.channel.Channel;
import model.chatcomponents.channel.IChannel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

import static org.junit.Assert.*;

public class ServerTest {
    IServer server;

    @BeforeClass
    public static void setUpBefore(){
        User.setPWEncryptor(new JBCryptAdapter());
    }

    @Before
    public void setUp() throws Exception {
        server = new Server(new DataHandler());
    }

    @After
    public void tearDown() throws Exception {
        server = null;
    }

    @Test
    public void getUserChannels() {
        IUser user = new MockUser("user","h");
        IChannel channel = new Channel("channel","desscription");
        channel.join(user);
        server.addChannel(channel);
        server.addUser(user);

        assertTrue(server.getUserChannels(user).contains(channel));
    }

    @Test
    public void getChannels() {
        IChannel channel = new Channel("channel","description");
        server.addChannel(channel);
        assertTrue(server.getChannels().size()==1);
        assertTrue(server.getChannels().contains(channel));
    }

    @Test
    public void getChannel() throws NoChannelFoundException {
        IChannel channel = new Channel("channel","description");
        server.addChannel(channel);
        assertTrue(server.getChannel(channel.getID()).equals(channel));
    }

    @Test(expected = NoChannelFoundException.class)
    public void getChannelNotFound() throws NoChannelFoundException {
        IChannel channel = new Channel("channel","description");
        server.addChannel(channel);
        server.getChannel(channel.getID()+1);
    }

    @Test(expected=NoSuchUserFoundException.class)
    public void getNotExistingUser() throws NoSuchUserFoundException, WrongPasswordException {
        IUser user = new User("Tobias", "password");
        server.addUser(user);
        IUser userTwo = server.getUser("Spondon", "password");
    }

    @Test(expected=WrongPasswordException.class)
    public void getUserWrongPassword() throws NoSuchUserFoundException, WrongPasswordException {
        IUser user = new User("Tobias", "password");
        server.addUser(user);
        IUser userTwo = server.getUser("Tobias", "jhfhjdfskhkajsdjas");
    }
    @Test(expected=NoSuchUserFoundException.class)
    public void getUser() throws NoSuchUserFoundException, WrongPasswordException {
        IUser user = new User("Tobias", "password");
        server.addUser(user);
        IUser userTwo = server.getUser("Spondon", "password");
        assertTrue(userTwo.equals(user));

    }

    @Test
    public void getAllUserNames() {
        IUser user1 = new User("MyName1", "password");
        IUser user2 = new User("MyName2", "passw2ord");
        IUser user3 = new User("MyName3", "passwor3d");

        server.addUser(user1);
        server.addUser(user2);
        server.addUser(user3);

        Collection<String> list = server.getAllUserNames();

        assertTrue(list.contains(user1.getName()));
        assertTrue(list.contains(user2.getName()));
        assertTrue(list.contains(user3.getName()));
    }

    @Test
    public void saveData() {
        IUser user1 = new User("MyName1", "password");
        IUser user2 = new User("MyName2", "passw2ord");
        server.addUser(user1);
        server.addUser(user2);

        IChannel channel1 = new Channel("ChannelName1", "MyDescription11");
        IChannel channel2 = new Channel("ChannelName2", "MyDescription222");
        server.addChannel(channel1);
        server.addChannel(channel2);

        server.saveData();

        IServer newServer = new Server(new DataHandler());

        Collection<String> list = newServer.getAllUserNames();
        assertTrue(list.contains(user1.getName()));
        assertTrue(list.contains(user2.getName()));

    }
}