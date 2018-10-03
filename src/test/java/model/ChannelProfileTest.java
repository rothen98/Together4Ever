package model;

import javafx.scene.image.Image;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ChannelProfileTest {
    ChannelProfile profile;
    @Before
    public void setUp() throws Exception {
        profile = new ChannelProfile("name",
                3,"description");

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getDescription() {
        assertEquals("description",profile.getDescription());
    }

    @Test
    public void getID() {
        assertEquals(3,profile.getID());
    }

    @Test
    public void getDisplayName() {
        assertEquals("name",profile.getDisplayName());
    }


}