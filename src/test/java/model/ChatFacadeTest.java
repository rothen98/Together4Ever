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
        server = facade.getServer();
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
        assertEquals(1, server.getChannels().size());

    }

    @Test
    public void getUserChannels() {
        IChannel channel = facade.createChannel("channel","descriiiiiiption",user);
        user = facade.createUser("Tubby", "D!nMamma123");
        channel.join(user);

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
        //IUser user = facade.getUser(username, password);
        assertTrue(1 == 1); // dont know what to test
    }

    @Test
    public void getChannel() {
        // should i test a getter??? pointlessss
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

    @Test
    public void getServer() {
    }
}