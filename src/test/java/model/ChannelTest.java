package model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;

public class ChannelTest {
    private IChannel channel;
    @Before
    public void setUp() throws Exception {
        channel = new Channel("Together4Ever",1);
    }

    @After
    public void tearDown() throws Exception {
        channel = null;
    }

    @Test
    public void getAllUserNames() {
        //Create channel and two users, let them join, and get all users names
        IUser userOne = new User("UserOne", "password");
        IUser userTwo = new User("UserTwo", "password");
        channel.join(userOne);
        channel.join(userTwo);

        Collection<String> names = channel.getAllUserNames();

        assertTrue(names.contains("UserOne"));
        assertTrue(names.contains("UserTwo"));
        assertTrue(names.size() == 2);

    }

    @Test
    public void getAllMessages() {
        //Create channel and two messages. Send the messages and check if the channel has two messages.
        IUser userOne = new User("UserOne", "password");
        LocalDateTime timeStampOne = LocalDateTime.of(2013,3,4,15,23);
        IUser userTwo = new User("UserTwo", "password");
        LocalDateTime timeStampTwo = LocalDateTime.of(2008,9,11,10,56);

        IMessage messageOne = new Message(userOne,"Hello my friends!",timeStampOne);
        IMessage messageTwo = new Message(userTwo,"Hi how are you?",timeStampTwo);

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
        LocalDateTime timeStampOne = LocalDateTime.of(2013,3,4,15,23);
        IUser userTwo = new User("UserTwo", "password");
        LocalDateTime timeStampTwo = LocalDateTime.of(2016,9,11,10,56);

        IMessage messageOne = new Message(userOne,"Hello my friends!",timeStampOne);
        IMessage messageTwo = new Message(userTwo,"Hi how are you?",timeStampTwo);

        channel.sendMessage(messageOne);
        channel.sendMessage(messageTwo);
        ;

        assertTrue(channel.getLastMessages(1).size()==1);

        //Perhaps we should check if the received messages holds the correct data?

    }

    @Test
    public void join() {
        IUser user = new MockUser("UserOne", "Password");
        channel.join(user);
        assertTrue(channel.getNumberOfUsers()==1);
    }

    @Test
    public void leave() {
        IUser user = new MockUser("UserOne", "Password");
        channel.join(user);
        channel.leave(user);
        assertTrue(channel.getNumberOfUsers()==0);
    }

    @Test
    public void getName() {
        //Create a channel and check if the method returns the given name
        assertTrue(channel.getName().equals("Together4Ever"));
    }

    @Test
    public void sendMessage(){
        MockUser userOne = new MockUser("UserOne", "password");
        LocalDateTime timeStampOne = LocalDateTime.of(2013,3,4,15,23);
        MockUser userTwo = new MockUser("UserTwo", "password");
        channel.join(userOne);
        channel.join(userTwo);
        IMessage messageOne = new Message(userOne,"Hello my friends!",timeStampOne);
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
        Channel otherChannel = new Channel("Together4Ever",2);
        assertFalse(channel.equals(otherChannel));

    }

    @Test
    public void getID(){
        assertTrue(channel.getID()==1);
    }
}