package model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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
        IUser user = new MockUser("user","h");
        IChannel channel = new Channel("channel","desscription",user);
        server.addChannel(channel);
        server.addUser(user);

        assertTrue(server.getUserChannels(user).contains(channel));
    }

    @Test
    public void getChannels() {
        IChannel channel = new Channel("channel","description",new MockUser("Sopberg","123"));
        server.addChannel(channel);
        assertTrue(server.getChannels().size()==1);
        assertTrue(server.getChannels().contains(channel));
    }

    @Test
    public void getChannel() throws NoChannelFoundException {
        IChannel channel = new Channel("channel","description",new MockUser("Sopberg","123"));
        server.addChannel(channel);
        assertTrue(server.getChannel(channel.getID()).equals(channel));
    }

    @Test(expected = NoChannelFoundException.class)
    public void getChannelNotFound() throws NoChannelFoundException {
        IChannel channel = new Channel("channel","description",new MockUser("Sopberg","123"));
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