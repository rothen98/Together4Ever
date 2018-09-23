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
        //just creating a message and checking that it cant be changed
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

        assertTrue(day == 23);
        //Ill do the same for the other variables

    }

    @Test
    public void getSender() {
        sender = new User("Name", "Password12");
        sender2 = new User("TestName", "Password23");

        assertNotEquals(sender, sender2);
        //Dont know what Ill do for test right now...
    }
}