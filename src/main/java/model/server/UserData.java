package model.server;

import model.chatcomponents.user.IUser;

/**
 * This class contains all the user data that is stored at the end of a session and fetched at the beginning.
 * The class is a middle ground for server to use
 * @author Alex Solberg
 */
public class UserData {
    private String username;
    private String password;
    private String displayName;
    private String displayImage;

    public UserData(String username, String password, String displayName, String displayImage) {
        this.username = username;
        this.password = password;
        this.displayName = displayName;
        this.displayImage = displayImage;
    }

    public UserData(IUser user) {
        this.username = user.getName();
        this.password = user.getHashedPassword();
        this.displayName = user.getDisplayName();
        this.displayImage = user.getDisplayImage();
    }


    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDisplayImage() {
        return displayImage;
    }
}
