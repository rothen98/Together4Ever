package model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.omg.DynamicAny.DynAnyFactoryPackage.InconsistentTypeCodeHelper;

import static org.junit.Assert.*;

public class ServerTest {
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
    public void getUserChannels() {
        IChannel channel = new Channel("channel",1);
        IUser user = new MockUser("user","h");
        server.addChannel(channel);
        server.addUser(user);
        channel.join(user);

        assertTrue(server.getUserChannels(user).contains(channel));
    }

    @Test
    public void getChannelNames() {
        IChannel channel = new Channel("channel",1);
        server.addChannel(channel);
        assertTrue(server.getChannelNames().size()==1);
        assertTrue(server.getChannelNames().contains("channel"));
    }

    @Test
    public void getChannel() {
        IChannel channel = new Channel("channel",1);
        server.addChannel(channel);
        assertTrue(server.getChannel(1).equals(channel));
    }

    @Test
    public void getUser() {
        fail("Test can't be implemented yet");
    }


}