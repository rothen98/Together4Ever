package model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class TextMessageTest {

    private TextMessage textMessage;
    private String message = "Testing Message";
    private IUser sender;
    private LocalDateTime timestamp;

    @Before
    public void setUp() throws Exception {
        textMessage = new TextMessage(sender, message, timestamp);
    }

    @After
    public void tearDown() throws Exception {
        textMessage = null;
    }

    @Test
    public void getTextMessage() {
        //Creating a textMessage and checking that it cant be changed
        String newTextMessage = textMessage.getTextMessage();
        String testTextMessage = "These wont be the same";
        //newMessage = testMessage;
        assertNotEquals(newTextMessage, testTextMessage);
        assertTrue(newTextMessage.length() == 15);
        sender = textMessage.getSender();
        timestamp = textMessage.getTimestamp();
        //assertFalse(sender.getName() == "");
        //assertTrue(timestamp.getYear() == 2018);

    }
}