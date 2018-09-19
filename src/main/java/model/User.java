package model;


import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Viktor Franzén
 *
 * This class contains the data of the User. Mainly an identifcation name and all it´s clients.
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
     * Sends a message to all the contained clients.
     * @param message The message that should be sent to the connected clients.
     */
    @Override
    public void sendMessageToClients(IMessage message) {
        clients.forEach(x -> x.update(message));
    }

    @Override
    public String getName() {
        return name;
    }
}
