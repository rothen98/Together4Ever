package model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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
        Channel channel = new Channel("Together4Ever");
        //Create two users, let them join, and get all users names
    }

    @Test
    public void getAllMessages() {
        Channel channel = new Channel("Together4Ever");
        //Create three messages, send them, and check if the channel's messages contains the same
        //info as the three created messages
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