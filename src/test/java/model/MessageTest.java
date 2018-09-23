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
        //Will probably test something more here.
    }

    @Test
    public void getTimestamp() {
        int day = timestamp.getDayOfMonth();
        int month = timestamp.getMonthValue();
        int year = timestamp.getYear();
        int hour = timestamp.getHour();
        int minute = timestamp.getMinute();
        int second = timestamp.getSecond();
        String sendTime = hour + ":" + minute;

        assertTrue(day == 23);
        assertTrue(month == 9);
        assertTrue(year == 2018);
        assertNotEquals(hour, 23);
        assertTrue(hour == timestamp.getHour());
        assertTrue(minute == timestamp.getMinute());
        assertNotEquals(second, 05);
        assertNotNull(timestamp);
        assertEquals(sendTime, hour+":"+minute);

    }

    @Test
    public void getSender() {
        sender = new User("Name", "Password12");
        sender2 = new User("TestName", "Password23");

        assertNotEquals(sender, sender2);
        assertNotNull(sender);
        assertNotNull(sender2);
        
        //Dont know what Ill do for test right now...
    }
}