package model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ChatFacadeTest {

    private ChatFacade facade;
    private IServer server;

    @Before
    public void setUp() throws Exception {
        facade = new ChatFacade();
    }

    @After
    public void tearDown() throws Exception {
        facade = null;
    }

    @Test
    public void createChannel() {
        String channelName = "This is my channel name";
        server = new Server();
        facade.createChannel(channelName);
        assertEquals(1, server.getChannelNames().size());
    }

    @Test
    public void getUserChannels() {
    }

    @Test
    public void createUSer() {
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