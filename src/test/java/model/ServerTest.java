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
        IChannel channel = new Channel("channel");
        IUser user = new MockUser("user","h");
        server.addChannel(channel);
        server.addUser(user);
        channel.join(user);

        assertTrue(server.getUserChannels(user).contains(channel));
    }

    @Test
    public void getChannelNames() {
        IChannel channel = new Channel("channel");
        server.addChannel(channel);
        assertTrue(server.getChannelNames().size()==1);
        assertTrue(server.getChannelNames().contains("channel"));
    }

    @Test
    public void getChannel() throws NoChannelFoundException {
        IChannel channel = new Channel("channel");
        server.addChannel(channel);
        assertTrue(server.getChannel(channel.getID()).equals(channel));
    }

    @Test(expected = NoChannelFoundException.class)
    public void getChannelNotFound() throws NoChannelFoundException {
        IChannel channel = new Channel("channel");
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
        IUser userTwo = server.getUser("Tobias", "hej");
    }
    @Test(expected=NoSuchUserFoundException.class)
    public void getUser() throws NoSuchUserFoundException, WrongPasswordException {
        IUser user = new User("Tobias", "password");
        server.addUser(user);
        IUser userTwo = server.getUser("Spondon", "password");
        assertTrue(userTwo.equals(user));

    }


}