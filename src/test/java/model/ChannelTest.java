package model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class ChannelTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getAllUserNames() {
        //Create channel and two users, let them join, and get all users names
        IChannel channel = new Channel("Together4Ever");
        User userOne = new User("UserOne", "password");
        User userTwo = new User("UserTwo", "password");
        channel.join(userOne);
        channel.join(userTwo);

        assertEquals("UserOne",userOne.getName());
        assertEquals("UserTwo",userTwo.getName());

    }

    @Test
    public void getAllMessages() {
        //Create three messages with different data, send them, and check if the channel's messages contains the same
        //info as the three created messages
        IChannel channel = new Channel("Together4Ever");
        IUser userOne = new User("UserOne", "password");
        LocalDateTime timeStampOne = LocalDateTime.of(2013,3,4,15,23);
        IUser userTwo = new User("UserTwo", "password");
        LocalDateTime timeStampTwo = LocalDateTime.of(2008,9,11,10,56);

        IMessage messageOne = new Message(userOne,"Hello my friends!",timeStampOne);
        IMessage messageTwo = new Message(userTwo,"Hi how are you?",timeStampTwo);
        IMessage messageThree = new Message(userOne,"Tjenixen",timeStampTwo);

        assertEquals(messageOne.getTimestamp().getHour(),15);
        assertTrue(messageOne.getSender().getName().equals("UserOne"));
        assertTrue(messageOne.getMessage().equals("Hello my friends!"));

        assertEquals(messageTwo.getTimestamp().getMinute(),56);
        assertTrue(messageTwo.getSender().getName().equals("UserTwo"));
        assertTrue(messageTwo.getMessage().equals("Hi how are you?"));

        assertEquals(messageThree.getTimestamp().getDayOfMonth(),11);
        assertTrue(messageThree.getSender().getName().equals("UserOne"));
        assertTrue(messageThree.getMessage().equals("Tjenixen"));
    }

    @Test
    public void getLastMessages() {
        //Create three messages, send team, and check if the channel's two latest messages contains the same
        // info as the two messages you sent last.
    }

    @Test
    public void join() {
    }

    @Test
    public void leave() {
    }

    @Test
    public void getName() {
        //Create a channel and check if the method returns the gicen name
    }
}