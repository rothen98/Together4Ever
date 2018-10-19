package model;

import model.chatcomponents.message.MessageContent;
import model.chatcomponents.message.MessageType;
import model.client.Client;
import model.client.IClient;
import model.client.IClientListener;
import model.chatcomponents.message.IMessage;
import model.chatcomponents.message.Message;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ClientTest {
    private IClient client;

    @Before
    public void setUp() throws Exception {
        client = new Client();
    }

    @After
    public void tearDown() throws Exception {
        client = null;
    }

    @Test
    public void updateListeners() {
        MockListener listener = new MockListener();
        client.addListeners(listener);

        MockUser userOne = new MockUser("UserOne", "password");
        IMessage messageOne = new Message(userOne,new MessageContent("Hello my friends!", MessageType.TEXT));

        client.updateListeners(null);
        assertTrue(listener.getAmountOfMessages() == 1);

    }

    @Test
    public void addListeners() {
        IClientListener listener = new MockListener();

        client.addListeners(listener);
        assertTrue(client.getAmountOfListeners() == 1);
    }

    @Test
    public void removeListeners() {
        IClientListener listener = new MockListener();

        client.addListeners(listener);
        client.removeListeners(listener);
        assertTrue(client.getAmountOfListeners() == 0);
    }
}