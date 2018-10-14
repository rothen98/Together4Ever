package model;


import org.mindrot.jbcrypt.BCrypt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Viktor Franzén
 *
 * This class contains the data of the User. Mainly an identifcation name, password and all it´s clients.
 */
public class User implements IUser{

    private Collection<IClient> clients;
    private String name;
    private String hashedPassword;

    private IRecognizable userProfile;


    public User(String name, String password){
        this.name = name;


        this.clients = new ArrayList<>();

        this.userProfile = new UserProfile(name);

        this.hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public User(String name, String password, String displayName, String displayImage) {
        this.name = name;
        this.hashedPassword = password;
        this.clients = new ArrayList<>();
        this.userProfile = new UserProfile(displayName, displayImage);
    }



    /**
     * Checks if the password is identical to the objects hashedpassword.
     * If true it adds it to the clients list.
     * @param client The client that wants to be connected
     * @param password The password that needs to match the objects password.
     */
    public void connectClient(IClient client, String password){
        if(BCrypt.checkpw(password,hashedPassword)){
            clients.add(client);
        }
    }


    /**
     * Checks if the client is in the clients collection. If so and the password is
     * matching the objects password it removes it.
     * Using a Arraylist to avoid a ConcurrentModificationError when removing while looping.
     * @param client The client that wants to be removed.
     * @param password The password that needs to match the objects password.
     */
    public void removeClient(IClient client, String password){
        List<IClient> removeThisClient = new ArrayList<>();
        clients.forEach(x -> {
            if(x.equals(client) && BCrypt.checkpw(password,hashedPassword)){
                removeThisClient.add(client);
            }
        });
        clients.remove(removeThisClient.get(0));
    }

    /**
     * A method mostly used in testing to see if clients are added and removed.
     * @return Returns the size of the clients list.
     */
    public int getAmountOfClients(){
        return clients.size();
    }

    /**
     * Sends a message to all the connected clients in the objects collection.
     * @param iIdentifiable
     */

    @Override
    public void sendMessageToClients(IIdentifiable iIdentifiable) {

        clients.forEach(x -> x.updateListeners(iIdentifiable));
    }

    /**
     * Checks if the two passwords match.
     * @param password The password that needs to be checked.
     * @return Returns true if the argument password is matching the objects.
     */
    @Override
    public boolean authorizeLogIn(String password) {
        if(BCrypt.checkpw(password,hashedPassword)){
            return true;
        }
        return false;
    }


    /**
     * @return Returns the value in the name variable.
     */
    @Override
    public String getName() {
        return name;
    }


    /**
     * Gets the users displayName from the UserProfile
     * @return the DisplayName
     */
    @Override
    public String getDisplayName(){
        return userProfile.getDisplayName();
    }

    /*public void setDisplayName(String displayName) {
        userProfile.setDisplayName(displayName);
    }

    public void setDisplayImage(String displayName) {
        userProfile.setDisplayImage(displayName);
    }*/

    /**
     * Gets the users displayImage from the UserProfile
     * @return the displayImage
     */
    @Override
    public String getDisplayImage() {
        return userProfile.getDisplayImage();
    }

    /**
     * @return the users password in an hashed format
     */
    @Override
    public String getHashedPassword() {
        return hashedPassword;
    }


    /**
     * Method to see if an object equals another. Overridden in case of using copies of the same object
     * in the future.
     * @param obj Object to compare with.
     */
    @Override
    public boolean equals(Object obj) {
        //Check if obj == this.
        if( obj == this){
            return true;
        }

        //Check if obj is an instance of user
        if(!(obj instanceof User)){
            return false;
        }

        //Typecast obj to user to be able to compare data.
        User u = (User) obj;

        if(this.name.equals(u.name) && this.getDisplayName().equals(u.getDisplayName())
                && this.getDisplayImage().equals(u.getDisplayImage())){
            return true;
        }
        return false;
    }
}
