package model.chatcomponents.message;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TextContentTest {

    private IMessageContent content;

    @Before
    public void setUp() throws Exception {
        content = new TextContent("test");
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
        assertEquals("test", content.getMessage());
    }
}