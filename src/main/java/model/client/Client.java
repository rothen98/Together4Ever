package model.client;

import model.identifiers.IIdentifiable;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Client class that is responsible to update the view when a new message is sent.
 *  @author Viktor Franzén
 */
public class Client implements IClient{
    private Collection<IClientListener> listeners;

    public Client(){
        listeners = new ArrayList<>();
    }

    /**
     * Updates all listeners with the sent message.
     * @param iIdentifiable The message to send to the listeners.
     */
    @Override
    public void updateListeners(IIdentifiable iIdentifiable) {
        listeners.forEach(x -> {
            x.update(iIdentifiable);
        });
    }

    /**
     * Adds an listener to the lists of listeners
     * @param listener The listener to add.
     */
    @Override
    public void addListeners(IClientListener listener) {
        listeners.add(listener);
    }

    /**
     * Removes an listener from the list of listeners
     * @param listener The listener to remove.
     */
    @Override
    public void removeListeners(IClientListener listener) {
        listeners.remove(listener);
    }

    /**
     * Checks the amount of listeners and return that number.
     * Mainly used to be able to test.
     * @return Returns amount of listeners
     */
    public int getAmountOfListeners(){
        return listeners.size();
    }


}
