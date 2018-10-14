package model;

import model.interaction.user.UserProfile;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserProfileTest {

    private UserProfile userProfile;

    @Before
    public void setUp() throws Exception {
        userProfile = new UserProfile("testUser");
    }

    @After
    public void tearDown() throws Exception {
        userProfile = null;
    }

    @Test
    public void getDisplayName() {
        assertEquals("testUser", userProfile.getDisplayName());
    }

    @Test
    public void getDisplayImage() {
        assertEquals("../../resources/default_user_pic.jpg", userProfile.getDisplayImage());
    }

}