package model.interaction.user;


import model.identifiers.IRecognizable;

/**
 * @author Viktor Franzén
 * The UserProfile class is supposed to contain the information that are going to be displayed
 * to other users in the application and should be easy to change for the user.
 */
public class UserProfile implements IRecognizable {

    private String displayName;
    private String displayImage;

    public UserProfile(String displayName){
        this.displayName = displayName;

        displayImage = "../../resources/default_user_pic.jpg";

    }

    public UserProfile(String displayName, String displayImage) {
        this.displayName = displayName;
        this.displayImage=displayImage;
    }

    /**
     * Returns the objects displayNam.
     * @return String with the displayName.
     */
    @Override
    public String getDisplayName(){
        return this.displayName;
    }

    /**
     * Returns the interaction displayImage.
     * @return The displayImage.
     */
    @Override
    public String getDisplayImage() {
        return displayImage;
    }

    public void setDisplayName(String displayName) {
        this.displayImage = displayName;
    }

    public void setDisplayImage(String displayImage) {
        this.displayImage = displayImage;
    }

}
