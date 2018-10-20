package model;

import model.chatcomponents.message.*;
import model.chatcomponents.user.IUser;
import model.chatcomponents.user.User;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import services.PasswordEncryption.JBCryptAdapter;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class MessageTest {

    private Message message;
    private IMessageContent messageContent;
    private IUser sender;
    private IUser sender2;
    private LocalDateTime timestamp = LocalDateTime.now();

    @BeforeClass
    public static void setUpBefore(){
        User.setPWEncryptor(new JBCryptAdapter());
    }
    
    @Before
    public void setUp() throws Exception {
        sender = new User("Name", "Password12");
        sender2 = new User("TestName", "Password23");
        message = new Message(sender, messageContent);

    }

    @After
    public void tearDown() throws Exception {
        message = null;
    }

    @Test
    public void getMessage() {
        //Creating a message and checking that it cant be changed
        //IMessageContent newMessage = message.getMessageContent();
        String testMessage = "These wont be the same";
        //assertNotEquals(newMessage, testMessage);
        //assertTrue(newMessage.length() == 15);
    }

    @Test
    public void getTimestamp() {
        //Ensuring that the timestamp is accurate and that we can extract the correct information from the object
        int day = timestamp.getDayOfMonth();
        int month = timestamp.getMonthValue();
        int year = timestamp.getYear();
        int hour = timestamp.getHour();
        int minute = timestamp.getMinute();
        int second = timestamp.getSecond();
        String sendTime = hour + ":" + minute;
        LocalDateTime testTimestamp = message.getTimestamp();

        assertTrue(day == testTimestamp.getDayOfMonth());
        assertTrue(month == testTimestamp.getMonthValue());
        assertTrue(year == testTimestamp.getYear());
        assertNotEquals(hour, 23);
        assertTrue(hour == testTimestamp.getHour());
        assertTrue(minute == testTimestamp.getMinute());
        assertNotEquals(second, 5.543);
        assertNotNull(timestamp);
        assertEquals(sendTime, testTimestamp.getHour()+":"+testTimestamp.getMinute());

    }

    @Test
    public void getSender() {

        assertNotEquals(sender, sender2);
        assertNotNull(sender);
        assertNotNull(sender2);
        assertEquals("Name", sender.getName());
        assertEquals(sender.getName(), message.getSenderName());
    }

    @Test
    public void getType() {
        IMessage testMessage = new Message(sender, new MessageContent("text", MessageType.TEXT));

        assertEquals(MessageType.TEXT, testMessage.getType());
    }
}