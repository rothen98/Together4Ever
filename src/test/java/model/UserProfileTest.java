package model;

import model.chatcomponents.user.UserProfile;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserProfileTest {

    private UserProfile userProfile;
    private UserProfile userProfile2;

    @Before
    public void setUp() throws Exception {
        userProfile = new UserProfile("testUser");
        userProfile2 = new UserProfile("testUser2", "test/path");
    }

    @After
    public void tearDown() throws Exception {
        userProfile = null;
    }

    @Test
    public void getDisplayName() {
        assertEquals("testUser", userProfile.getDisplayName());
        assertEquals("testUser2", userProfile2.getDisplayName());
    }

    @Test
    public void getDisplayImage() {
        assertEquals("../../resources/default_user_pic.jpg", userProfile.getDisplayImage());
        assertEquals("test/path", userProfile2.getDisplayImage());
    }

    @Test
    public void setDisplayName() {
        userProfile.setDisplayName("123test");
        assertEquals("123test", userProfile.getDisplayName());
    }

    @Test
    public void setDisplayImage() {
        userProfile.setDisplayImage("new/test/path");
        assertEquals("new/test/path", userProfile.getDisplayImage());
    }
}