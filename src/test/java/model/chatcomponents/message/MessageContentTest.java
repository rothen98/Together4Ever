package model.chatcomponents.message;

import model.client.Client;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MessageContentTest {
    IMessageContent content;

    @Before
    public void setUp() throws Exception {
        content = new MessageContent("text",MessageType.TEXT);
    }

    @After
    public void tearDown() throws Exception {
        content = null;
    }

    @Test
    public void getType() {
        assertEquals(MessageType.TEXT, content.getType());
    }

    @Test
    public void getMessage() {
        assertEquals("text", content.getMessage());
    }
}