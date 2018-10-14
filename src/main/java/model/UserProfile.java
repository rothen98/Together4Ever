package model;


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




    /**
     * Returns the objects displayNam.
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
    public String getDisplayImage() {
        return displayImage;
    }
}
