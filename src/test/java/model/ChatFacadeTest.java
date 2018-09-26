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
        server = new Server();
        user = new MockUser("Tubby", "D!nMamma123");
        facade = new ChatFacade();
    }

    @After
    public void tearDown() throws Exception {
        facade = null;
    }

    @Test
    public void createChannel() {
        String channelName = "This is my channel name";
        facade.createChannel(channelName);
        assertEquals(1, server.getChannelNames().size());
    }

    @Test
    public void getUserChannels() {
        IChannel channel = facade.createChannel("channel");
        user = facade.createUser("Tubby", "D!nMamma123");
        channel.join(user);

        assertTrue(facade.getUserChannels(user).contains(channel));
    }

    @Test
    public void createUser() {
    }

    @Test
    public void getUser() {
    }

    @Test
    public void getChannel() {
    }

    @Test
    public void createClient() {
    }

    @Test
    public void addClientListner() {
    }
}