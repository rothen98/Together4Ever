package model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;

public class MessageTest {

    private Message message;
    private String textMessage = "Testing Message";
    private IUser sender;
    private IUser sender2;
    private LocalDateTime timestamp = LocalDateTime.now();

    @Before
    public void setUp() throws Exception {
        message = new Message(sender, textMessage, timestamp);
    }

    @After
    public void tearDown() throws Exception {
        message = null;
    }

    @Test
    public void getMessage() {
        //Creating a message and checking that it cant be changed
        String newMessage = message.getMessage();
        String testMessage = "These wont be the same";
        //newMessage = testMessage;
        assertNotEquals(newMessage, testMessage);
        assertTrue(newMessage.length() == 15);
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
        LocalDateTime testTimestamp = LocalDateTime.now();

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
        //Create a sender for the message and check to make sure they aren't empty
        sender = new User("Name", "Password12");
        sender2 = new User("TestName", "Password23");

        assertNotEquals(sender, sender2);
        assertNotNull(sender);
        assertNotNull(sender2);
        assertTrue(sender.getName() == "Name");
    }
}