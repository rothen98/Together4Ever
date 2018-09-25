package model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class UserTest {
    private IUser user;
    private IClient client;

    
    @Before
    public void setUp() throws Exception{
        user = new User("testUser","password");
        client = new Client();
    }

    @After
    public void tearDown() throws Exception{
        user = null;
        client = null;
    }

    @Test
    public void connectClient() {
        IClient client2 = new Client();
        user.connectClient(client, "password");
        user.connectClient(client2, "password");

        assertTrue(user.getAmountOfClients() == 2);


    }

    @Test
    public void removeClient() {
        user.connectClient(client, "password");
        user.removeClient(client, "password");

        assertTrue(user.getAmountOfClients() == 0);
    }

    @Test
    public void sendMessageToClients() {
        MockClient mockclient = new MockClient();
        user.connectClient(mockclient, "password");

        LocalDateTime timeStampOne = LocalDateTime.of(2013,3,4,15,23);
        IMessage messageOne = new Message(user,"Hello my friends!",timeStampOne);

        user.sendMessageToClients(messageOne);

        assertTrue(mockclient.getAmountOfMessages() == 1);
    }

    @Test
    public void getName() {
        assertEquals("testUser", user.getName());
    }


    @Test
    public void getAmountOfClients() {
        user.connectClient(client, "password");
        assertTrue(user.getAmountOfClients() == 1);
    }
}