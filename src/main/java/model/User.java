package model;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Viktor Franzén
 *
 * This class contains the data of the User. Mainly an identifcation name, password and all it´s clients.
 */
public class User implements IUser {

    private Collection<IClient> clients;
    private String name;
    private String password;

    public User(String name, String password){
        this.name = name;
        this.password = password;
        this.clients = new ArrayList<>();
    }



    /**
     * Checks if the password is identical to the objects password.
     * If true it adds it to the clients list.
     * @param client The client that wants to be connected
     * @param password The password that needs to match the objects password.
     */
    public void connectClient(IClient client, String password){
        if(this.password.equals(password)){
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
            if(x.equals(client) && this.password.equals(password)){
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
     * @param message The message that should be sent to the connected clients.
     */

    @Override
    public void sendMessageToClients(IMessage message) {

        clients.forEach(x -> x.update(message));
    }


    /**
     * @return Returns the value in the name variable.
     */
    @Override
    public String getName() {
        return name;
    }

}
