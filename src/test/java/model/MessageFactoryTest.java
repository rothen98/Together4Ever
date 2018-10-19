package model;

import model.chatcomponents.message.MessageFactory;
import model.chatcomponents.message.TextContent;
import model.chatcomponents.user.IUser;
import model.chatcomponents.user.User;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import services.PasswordEncryption.JBCryptAdapter;


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
        TextContent messageContent = new TextContent(messageText);

        assertEquals(messageText,
                MessageFactory.createTextMessage(messageText,messageSender).getMessage());
        assertEquals(messageText,
                MessageFactory.createTextMessage(messageContent,messageSender).getMessage());
    }


    @Test
    public void createImageMessage() {
        String path = "/path/to/image";
        ImageContent messageContent = new ImageContent(path);

        assertEquals(path,
                MessageFactory.createImageMessage(path,messageSender).getMessage());
        assertEquals(path,
                MessageFactory.createImageMessage(messageContent,messageSender).getMessage());
    }


    @Test
    public void createJoinMessage() {
        String message = messageSender.getName() + " just joined the channel.";

        assertEquals(message,
                MessageFactory.createJoinMessage(messageSender).getMessage());

    }

    @Test
    public void createLeaveMessage() {
        String message = messageSender.getName() + " just left the channel.";

        assertEquals(message,
                MessageFactory.createLeaveMessage(messageSender).getMessage());

    }

    @Test
    public void createChannelMessage() {
        String message = "Test message 12345.";

        assertEquals(message,
                MessageFactory.createChannelMessage(message,messageSender).getMessage());

    }


    @Test
    public void createMessageWithTimestamp() {
        LocalDateTime timestamp = LocalDateTime.of(1998,2,8,10,55);
        TextContent content = new TextContent("test");



        assertEquals(timestamp,
                MessageFactory.createMessageWithTimestamp(messageSender,content,timestamp).getTimestamp());
        assertEquals("test",
                MessageFactory.createMessageWithTimestamp(messageSender,content,timestamp).getMessage());
    }
}