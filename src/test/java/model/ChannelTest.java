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
        channel = new Channel("Together4Ever");
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
        //Create channel and three messages with different data, send them, and check if the channel's messages contains the same
        //info as the three created messages
        IUser userOne = new User("UserOne", "password");
        LocalDateTime timeStampOne = LocalDateTime.of(2013,3,4,15,23);
        IUser userTwo = new User("UserTwo", "password");
        LocalDateTime timeStampTwo = LocalDateTime.of(2008,9,11,10,56);

        IMessage messageOne = new Message(userOne,"Hello my friends!",timeStampOne);
        IMessage messageTwo = new Message(userTwo,"Hi how are you?",timeStampTwo);

        channel.sendMessage(messageOne);
        channel.sendMessage(messageTwo);


        List<IMessage> messages = channel.getAllMessages();

        assertEquals(messages.get(0).getTimestamp().getHour(),15);
        assertTrue(messages.get(0).getSender().getName().equals("UserOne"));
        assertTrue(messages.get(0).getMessage().equals("Hello my friends!"));

        assertEquals(messages.get(1).getTimestamp().getMinute(),56);
        assertTrue(messages.get(1).getSender().getName().equals("UserTwo"));
        assertTrue(messages.get(1).getMessage().equals("Hi how are you?"));


    }

    @Test
    public void getLastMessages() {
        //Create a channel and three messages, send team, and check if the channel's two latest messages contains the same
        // info as the two messages you sent last.
        IUser userOne = new User("UserOne", "password");
        LocalDateTime timeStampOne = LocalDateTime.of(2013,3,4,15,23);
        IUser userTwo = new User("UserTwo", "password");
        LocalDateTime timeStampTwo = LocalDateTime.of(2008,9,11,10,56);

        IMessage messageOne = new Message(userOne,"Hello my friends!",timeStampOne);
        IMessage messageTwo = new Message(userTwo,"Hi how are you?",timeStampTwo);
        IMessage messageThree = new Message(userOne,"Tjenixen",timeStampTwo);

        channel.sendMessage(messageOne);
        channel.sendMessage(messageTwo);
        channel.sendMessage(messageThree);

        List<IMessage> messages = channel.getLastMessages(2);

        //This should equal messageTwo's data
        assertEquals(messages.get(0).getTimestamp().getMinute(),56);
        assertTrue(messages.get(0).getSender().getName().equals("UserTwo"));
        assertTrue(messages.get(0).getMessage().equals("Hi how are you?"));

        //This should equal messageThree's data
        assertEquals(messages.get(1).getTimestamp().getDayOfMonth(),11);
        assertTrue(messages.get(1).getSender().getName().equals("UserOne"));
        assertTrue(messages.get(1).getMessage().equals("Tjenixen"));

        assertFalse(messages.get(0).getMessage().equals("Hello my friends!"));


    }

    @Test
    public void join() {
        IUser user = new User("UserOne", "Password");
        channel.join(user);
        assertTrue(channel.getAllUserNames().contains("UserOne"));
    }

    @Test
    public void leave() {
        IUser user = new User("UserOne", "Password");
        Channel channel = new Channel("Channel");
        channel.join(user);
        channel.leave(user);
        assertFalse(channel.getAllUserNames().contains("UserOne"));
    }

    @Test
    public void getName() {
        //Create a channel and check if the method returns the given name
        assertTrue(channel.getName().equals("Together4Ever"));
    }

    @Test
    public void sendMessage(){
        IUser userOne = new MockUser("UserOne", "password");
        LocalDateTime timeStampOne = LocalDateTime.of(2013,3,4,15,23);
        MockUser userTwo = new MockUser("UserTwo", "password");
        channel.join(userOne);
        channel.join(userTwo);
        IMessage messageOne = new Message(userOne,"Hello my friends!",timeStampOne);
        channel.sendMessage(messageOne);
        assertEquals("Hello my friends!",userTwo.getReceivedMessages().get(0).getMessage());


    }
}