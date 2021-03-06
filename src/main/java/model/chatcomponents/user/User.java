package model.chatcomponents.user;

import model.client.IClient;
import model.identifiers.IIdentifiable;
import model.identifiers.IRecognizable;

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
    private static PasswordEncryptor pwEncryptor;

    private IRecognizable userProfile;


    public User(String name, String password){
        this.name = name;


        this.clients = new ArrayList<>();

        this.userProfile = new UserProfile(name);

        this.hashedPassword = pwEncryptor.hashPassword(password, pwEncryptor.generateSalt());

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
     * @param password The password that needs to match the chatcomponents password.
     */
    public void connectClient(IClient client, String password){
        if(pwEncryptor.checkPassword(password,hashedPassword)){
            clients.add(client);
        }
    }


    /**
     * Checks if the client is in the clients collection. If it is and it´s equal to the one
     * as an argument it removes it.
     * Using a Arraylist to avoid a ConcurrentModificationError when removing while looping.
     * @param client The client that wants to be removed.
     */
    public void removeClient(IClient client){
        List<IClient> removeThisClient = new ArrayList<>();
        clients.forEach(x -> {
            if(x.equals(client)){
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
     * Sends an update to all the connected clients.
     * @param iIdentifiable the channel that has been updated
     */

    @Override
    public void updateClients(IIdentifiable iIdentifiable) {

        clients.forEach(x -> x.updateListeners(iIdentifiable));
    }

    /**
     * Checks if the two passwords match.
     * @param password The password that needs to be checked.
     * @return Returns true if the argument password is matching the chatcomponents.
     */
    @Override
    public boolean authorizeLogIn(String password) {
        return pwEncryptor.checkPassword(password, hashedPassword);
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

/*    public void setDisplayName(String displayName) {
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

    public static void setPWEncryptor(PasswordEncryptor encryptor){
        pwEncryptor = encryptor;
    }
}
