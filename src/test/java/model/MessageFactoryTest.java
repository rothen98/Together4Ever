package model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.xml.soap.Text;

import static org.junit.Assert.*;

public class MessageFactoryTest {
    IUser messageSender;

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
                MessageFactory.createTextMessage(messageText,messageSender).getMessageContent().getMessage());
        assertEquals(messageText,
                MessageFactory.createTextMessage(messageContent,messageSender).getMessageContent().getMessage());
    }


    @Test
    public void createImageMessage() {
        String path = "/path/to/image";
        ImageContent messageContent = new ImageContent(path);

        assertEquals(path,
                MessageFactory.createImageMessage(path,messageSender).getMessageContent().getMessage());
        assertEquals(path,
                MessageFactory.createImageMessage(messageContent,messageSender).getMessageContent().getMessage());
    }


    @Test
    public void createJoinMessage() {
        String message = messageSender.getName() + " just joined the channel.";

        assertEquals(message,
                MessageFactory.createJoinMessage(messageSender).getMessageContent().getMessage());

    }

    @Test
    public void createLeaveMessage() {
        String message = messageSender.getName() + " just left the channel.";

        assertEquals(message,
                MessageFactory.createLeaveMessage(messageSender).getMessageContent().getMessage());

    }

    @Test
    public void createChannelMessage() {
        String message = "Test message 12345.";

        assertEquals(message,
                MessageFactory.createChannelMessage(message,messageSender).getMessageContent().getMessage());

    }
}