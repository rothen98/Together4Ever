package model;

import model.client.Client;
import model.client.IClient;
import model.chatcomponents.message.IMessage;
import model.chatcomponents.message.IMessageContent;
import model.chatcomponents.message.Message;
import model.chatcomponents.message.TextContent;
import model.chatcomponents.user.IUser;
import model.chatcomponents.user.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {
    private User user;
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

        //LocalDateTime timeStampOne = LocalDateTime.of(2013,3,4,15,23);
        IMessageContent messageContent = new TextContent("Hello my friends!");
        IMessage messageOne = new Message(user,messageContent);

        user.sendMessageToClients(null);

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

    @Test
    public void authorizeLogIn() {
        assertTrue(user.authorizeLogIn("password"));
        assertFalse(user.authorizeLogIn("wrongpassword"));
    }

    @Test
    public void equals() {
        IUser user2 = new User("user2", "password");
        IUser copyUser = new User("testUser", "password");

        assertFalse(user.equals(user2));
        assertTrue(user.equals(user));
        assertTrue(user.equals(copyUser));
        assertFalse(user.equals(client));

    }

    @Test
    public void getDisplayName() {
        assertEquals("testUser", user.getDisplayName());
    }

    @Test
    public void getDisplayImage() {
        assertEquals("../../resources/default_user_pic.jpg", user.getDisplayImage());
    }
}