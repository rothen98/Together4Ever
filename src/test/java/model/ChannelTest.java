package model;

import model.identifiers.IRecognizable;
import model.chatcomponents.message.*;
import model.chatcomponents.user.IUser;
import model.chatcomponents.user.User;
import model.chatcomponents.channel.Channel;
import model.chatcomponents.channel.IChannel;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import services.PasswordEncryption.JBCryptAdapter;

import java.util.Collection;

import static org.junit.Assert.*;

public class ChannelTest {
    private IChannel channel;

    @BeforeClass
    public static void setUpBefore(){
        User.setPWEncryptor(new JBCryptAdapter());
    }

    @Before
    public void setUp() throws Exception {
        /*
        IMPORTANT!!!
        When joining a user in a channel a message is sent. The length of it will therefore be 1 at the start.
         */
        channel = new Channel("Together4Ever", "The best channel");
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

        Collection<IRecognizable> users = channel.getAllUsersInfo();
        assertEquals(2,users.size());



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

        //2 sent messages, 1 join message from base user joining.
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
        assertTrue(channel.getNumberOfUsers()==1);
        channel.hasUser(user);
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
        assertTrue(channel.getDisplayName().equals("Together4Ever"));
    }

    @Test
    public void sendMessage(){
        MockUser userOne = new MockUser("UserOne", "password");
        MockUser userTwo = new MockUser("UserTwo", "password");

        channel.join(userOne);
        channel.join(userTwo);

        IMessage messageOne = MessageFactory.createTextMessage("Hello my friends!",userOne);
        channel.sendMessage(messageOne);

        assertEquals(2,userTwo.getNumberOfReceivedMessages());
        assertEquals(3,userOne.getNumberOfReceivedMessages());


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
        Channel otherChannel = new Channel("Together4Ever","none");
        assertFalse(channel.equals(otherChannel));

    }

    @Test
    public void getID(){
        IChannel otherChannel = new Channel("otherChannel","none");
        assertTrue(channel.getID() +1 == otherChannel.getID());
    }

    @Test
    public void getNumberOfUsers() {
        channel.join(new MockUser("namn","password"));
        assertEquals(1,channel.getNumberOfUsers());
    }

    @Test
    public void getDisplayName() {
        IChannel someChannel = new Channel("theName","NA");
        assertEquals("theName",someChannel.getDisplayName());
    }


    @Test
    public void getDescription() {
        IChannel someChannel = new Channel("theName","TDA367 group work");
        assertEquals("TDA367 group work",someChannel.getDescription());
    }

    @Test
    public void isChannelAdministrator(){
        IChannel someChannel = new Channel("theName","TDA367 group work");
        User user = new User("Test","test");
        someChannel.join(user);
        assertTrue(someChannel.isChannelAdministrator(user));
    }
}