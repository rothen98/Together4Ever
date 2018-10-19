package model;

import model.chatcomponents.message.IMessage;
import model.chatcomponents.message.MessageContent;
import model.chatcomponents.message.MessageFactory;
import model.chatcomponents.message.MessageType;
import model.chatcomponents.user.IUser;
import model.chatcomponents.user.User;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import services.PasswordEncryption.JBCryptAdapter;


import java.awt.*;
import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class MessageFactoryTest {
    IUser messageSender;

    @BeforeClass
    public static void setUpBefore(){
        User.setPWEncryptor(new JBCryptAdapter());
    }

    @Before
    public void setUp() throws Exception {
        messageSender = new User("messageSender", "password");
    }

    @After
    public void tearDown() throws Exception {
        messageSender = null;
    }

    @Test
    public void createTextMessage() {
        String messageText = "hej jag heter gustav";
        IMessage message = MessageFactory.createTextMessage(messageText,messageSender);

        assertEquals(messageText, message.getMessage());
        assertEquals(MessageType.TEXT, message.getType());

    }


    @Test
    public void createImageMessage() {
        String path = "/path/to/image";
        IMessage message = MessageFactory.createImageMessage(path,messageSender);


        assertEquals(path, message.getMessage());
        assertEquals(MessageType.IMAGE, message.getType());
    }


    @Test
    public void createJoinMessage() {
        String messageText = messageSender.getName() + " just joined the channel.";
        IMessage message = MessageFactory.createJoinMessage(messageSender);

        assertEquals(messageText, message.getMessage());
        assertEquals(MessageType.JOIN, message.getType());

    }

    @Test
    public void createLeaveMessage() {
        String messageText = messageSender.getName() + " just left the channel.";
        IMessage message = MessageFactory.createLeaveMessage(messageSender);


        assertEquals(messageText, message.getMessage());
        assertEquals(MessageType.LEAVE, message.getType());

    }

    @Test
    public void createKickMessage(){
        String messageText = messageSender.getName() + " has just been kicked from the channel.";
        IMessage message = MessageFactory.createKickMessage(messageSender);

        assertEquals(messageText, message.getMessage());
        assertEquals(MessageType.KICK, message.getType());
    }

    @Test
    public void createChannelMessage() {
        String messageText = "Test message 12345.";
        IMessage message = MessageFactory.createChannelMessage(messageText,messageSender);

        assertEquals(messageText, message.getMessage());
        assertEquals(MessageType.CHANNEL, message.getType());

    }


    @Test
    public void createMessageWithTimestamp() {
        LocalDateTime timestamp = LocalDateTime.of(1998,2,8,10,55);
        MessageContent content = new MessageContent("test", MessageType.TEXT);



        assertEquals(timestamp,
                MessageFactory.createMessageWithTimestamp(messageSender,content,timestamp).getTimestamp());
        assertEquals("test",
                MessageFactory.createMessageWithTimestamp(messageSender,content,timestamp).getMessage());
    }
}