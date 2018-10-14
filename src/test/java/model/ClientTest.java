package model;

import model.client.Client;
import model.client.IClient;
import model.client.IClientListener;
import model.interaction.message.IMessage;
import model.interaction.message.Message;
import model.interaction.message.TextContent;
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
        IMessage messageOne = new Message(userOne,new TextContent("Hello my friends!"));

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