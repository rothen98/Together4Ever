package model.chatcomponents.message;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ChannelContentTest {
    private ChannelContent content;

    @Before
    public void setUp() throws Exception {
        content = new ChannelContent("test");
    }

    @After
    public void tearDown() throws Exception {
        content = null;
    }

    @Test
    public void getType() {
        assertEquals(MessageType.CHANNEL, content.getType());
    }

    @Test
    public void getMessage() {
        assertEquals("test", content.getMessage());
    }
}