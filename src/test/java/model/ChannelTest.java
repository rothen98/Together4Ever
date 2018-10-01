package model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;

public class ChannelTest {
    private IChannel channel;
    @Before
    public void setUp() throws Exception {
        channel = new Channel("Together4Ever", "The best channel", new User("Tobias", "test"));
    }

    @After
    public void tearDown() throws Exception {
        channel = null;
    }

    @Test
    public void getAllUsers() {
        //Create channel and two users, let them join, and get all users names
        IUser userOne = new User("UserOne", "password");
        IUser userTwo = new User("UserTwo", "password");
        channel.join(userOne);
        channel.join(userTwo);

        Collection<IIdentifiable> users = channel.getAllUsers();
        assertEquals(3,users.size());



    }

    @Test
    public void getAllMessages() {
        //Create channel and two messages. Send the messages and check if the channel has two messages.
        IUser userOne = new User("UserOne", "password");
        IUser userTwo = new User("UserTwo", "password");

        IMessageContent textMessageOne = new TextContent("Hello my friends");
        IMessageContent textMessageTwo = new TextContent("Hi how are you?");

        IMessage messageOne = new Message(userOne,textMessageOne);
        IMessage messageTwo = new Message(userTwo,textMessageTwo);

        channel.sendMessage(messageOne);
        channel.sendMessage(messageTwo);

        assertTrue(channel.getAllMessages().size() == 2);

        //Perhaps we should check if the received messages holds the correct data?
    }

    @Test
    public void getLastMessages() {
        //Create a channel and three messages, send team, and check if the channel's two latest messages contains the same
        // info as the two messages you sent last.
        IUser userOne = new MockUser("UserOne", "password");
        IUser userTwo = new User("UserTwo", "password");

        IMessage messageOne = new Message(userOne,new TextContent("Hello my friends!"));
        IMessage messageTwo = new Message(userTwo,new TextContent("Hi how are you?"));

        channel.sendMessage(messageOne);
        channel.sendMessage(messageTwo);

        assertEquals(1,channel.getLastMessages(1).size());

        //Perhaps we should check if the received messages holds the correct data?

    }

    @Test
    public void join() {
        IUser user = new MockUser("UserOne", "Password");
        channel.join(user);
        channel.join(null);
        assertTrue(channel.getNumberOfUsers()==2);
        channel.hasUser(user);
    }

    @Test
    public void leave() {
        IUser user = new MockUser("UserOne", "Password");
        channel.join(user);
        channel.leave(user);
        assertTrue(channel.getNumberOfUsers()==1);
    }

    @Test
    public void getName() {
        //Create a channel and check if the method returns the given name
        assertTrue(channel.getDisplayName().equals("Together4Ever"));
    }

    @Test
    public void sendMessage(){
        MockUser userOne = new MockUser("UserOne", "password");
        LocalDateTime timeStampOne = LocalDateTime.of(2013,3,4,15,23);
        MockUser userTwo = new MockUser("UserTwo", "password");
        channel.join(userOne);
        channel.join(userTwo);
        IMessage messageOne = new Message(userOne,new TextContent("Hello my friends!"));
        channel.sendMessage(messageOne);
        assertEquals(1,userTwo.getReceivedMessages().size());
        assertEquals(1,userOne.getReceivedMessages().size());


    }

    @Test
    public void hasUser(){
        IUser user = new MockUser("user","password");
        IUser userTwo = new MockUser("userTwo","password");
        channel.join(user);
        assertTrue(channel.hasUser(user));
        assertFalse(channel.hasUser(userTwo));
    }

    @Test
    public void equals(){
        Channel otherChannel = new Channel("Together4Ever","none",new User("Tobias", "test"));
        assertFalse(channel.equals(otherChannel));

    }

    @Test
    public void getID(){
        IChannel otherChannel = new Channel("otherChannel","none", new User("Tobias", "test"));
        assertTrue(channel.getID() +1 == otherChannel.getID());
    }

    @Test
    public void getNumberOfUsers() {
        assertEquals(1,channel.getNumberOfUsers());
    }

    @Test
    public void getDisplayName() {
        IChannel someChannel = new Channel("theName","NA",null);
        assertEquals("theName",someChannel.getDisplayName());
    }


    @Test
    public void getDescription() {
        IChannel someChannel = new Channel("theName","TDA367 group work",null);
        assertEquals("TDA367 group work",someChannel.getDescription());
    }
}