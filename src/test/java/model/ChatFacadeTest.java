package model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ChatFacadeTest {

    private ChatFacade facade;
    private IServer server;
    private IUser user;

    @Before
    public void setUp() throws Exception {
        facade = new ChatFacade();
        server = new Server();
        user = new MockUser("Tubby", "D!nMamma123");
    }

    @After
    public void tearDown() throws Exception {
        facade = null;
    }

    @Test

    public void createChannel() throws NoChannelFoundException {
        String channelName = "This is my channel name";
        IChannel channel = facade.createChannel(channelName, "descriptiiiiiiion",user);
        int id = channel.getID();
        assertEquals(channel, facade.getChannel(id));
    }

    @Test
    public void getUserChannels() {
        IChannel channel = facade.createChannel("channel","descriiiiiiption",user);
        user = facade.createUser("Tubby", "D!nMamma123");
        channel.join(user);
        assertTrue(facade.getUserChannels(user).size() == 1);
        assertTrue(facade.getUserChannels(user).contains(channel));
    }

    @Test
    public void createUser() throws NoSuchUserFoundException, WrongPasswordException {
        String username = "Username";
        String password = "Password";
        IUser user = facade.createUser(username, password);
        IUser serverUser = server.getUser(username,password);
        assertEquals(user, serverUser);
    }

    @Test
    public void getUser() throws NoSuchUserFoundException, WrongPasswordException {
        String username = "Username";
        String password = "Password";
        IUser user = facade.createUser(username, password);
        IUser user2 = facade.getUser(username, password);
        assertTrue(user == user2);
        assertTrue(facade.getUserChannels(user).size() == 0);
    }

    @Test
    public void getChannel() throws NoChannelFoundException {
        String channelName = "this is my name";
        String description = "This is my description";
        IChannel channel = facade.createChannel(channelName, description, user);
        int id = channel.getID();
        IChannel testChannel = facade.getChannel(id);
        assertEquals(testChannel, channel);
    }

    @Test
    public void createClient() {
        IClient client = facade.createClient();

    }

    @Test
    public void addClientListener() {
    }

    @Test
    public void deleteClientListener() {

    }

    @Test
    public void createTextMessage() {
    }

    @Test
    public void createImageMessage() {
    }
}