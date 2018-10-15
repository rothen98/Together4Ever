package model.chatcomponents.message;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ImageContentTest {

    private IMessageContent content;

    @Before
    public void setUp() throws Exception {
        content = new ImageContent("/test/path");
    }

    @After
    public void tearDown() throws Exception {
        content = null;
    }

    @Test
    public void getType() {
        assertEquals(MessageType.IMAGE, content.getType());
    }

    @Test
    public void getMessage() {
        assertEquals("/test/path", content.getMessage());
    }
}