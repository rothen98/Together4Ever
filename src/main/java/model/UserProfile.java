package model;


import javafx.scene.image.Image;

/**
 * @author Viktor Franzén
 * The UserProfile class is supposed to contain the information that are going to be displayed
 * to other users in the application and should be easy to change for the user.
 */
public class UserProfile implements IIdentifiable {

    private String displayName;
    private Image displayImage;

    public UserProfile(String displayName){
        this.displayName = displayName;
        //Set displayImage to the path to an default displayImage
    }




    /**
     * Returns the objects displayName.
     * @return String with the displayName.
     */
    @Override
    public String getDisplayName(){
        return this.displayName;
    }

    /**
     * Returns the objects displayImage.
     * @return The displayImage.
     */
    @Override
    public Image getDisplayImage() {
        return null;
    }
}
