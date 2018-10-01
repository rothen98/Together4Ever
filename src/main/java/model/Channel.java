package model;

import javafx.scene.image.Image;

import java.awt.*;
import java.time.LocalDate;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Tobias Lindroth
 *
 * 
 * The Channel class contains data about the channel's users and messages.
 * There are methods to get the messages, the users names and also methods for joining and leaving the channel.
 */
public class Channel implements IChannel {
    private final Collection<IUser> users;
    private final List<IMessage> messages;

    private IInformative channelProfile;

    /**
     * This fields ensures that every channel gets an unique id.
     */
    private static final AtomicInteger idCounter = new AtomicInteger(0);

    public Channel(String name,String description,IUser creator) {
        channelProfile = new ChannelProfile(name,null,idCounter.getAndIncrement(),description);
        join(creator);
        this.users = new HashSet<>();
        this.messages = new ArrayList<>();

    }

    /**
     * This method will return all the users
     * @return a collection with the users
     */
    @Override
    public Collection<IIdentifiable> getAllUsers() {
        //Will be implemented in another way when user is changed
        Collection<IIdentifiable> returnCollection = new ArrayList<>();
        users.forEach(s ->{
            returnCollection.add((IIdentifiable)s);
        });

        return returnCollection;
    }

    /**
     * This method will return a copy of all the messages that a channel contains
     * @return a list with messages
     */
    @Override
    public List<IMessage> getAllMessages() {
        return copyOfList(messages);
    }

    /**
     * This method will copy a list
     * @param list the list to be copied
     * @param <T> any object
     * @return the copy
     */
    private <T> List<T> copyOfList(List<T> list) {
        List<T> listToReturn = new ArrayList<>();
        for(T element:list){
            listToReturn.add(element);
        }
        return listToReturn;
    }

    /**
     * This method will return the @param amount latest messages that were sent to the channel.
     * @param amount The numbers of messages you wish to be returned
     * @return A list with messages
     */
    @Override
    public List<IMessage> getLastMessages(int amount) {
        return copyOfList(messages.subList(messages.size()-amount,messages.size()));
        //Is copy of list really needed?
    }

    /**
     * This method returns how many users the channel has
     * @return number of users
     */
    @Override
    public int getNumberOfUsers() {
        return users.size();
    }

    /**
     * This method sends the given to all it's users.
     * @param message The message to be broadcast to all users.
     */
    @Override
    public void sendMessage(IMessage message) {
        messages.add(message);
        for(IUser user: users){
            user.sendMessageToClients(message);
        }
    }

    /**
     * This method will add the user to the channel
     * @param user the user that are to join the channel
     */
    @Override
    public void join(IUser user) {
        users.add(user);
    }

    /**
     * This method will remove the user from the channel
     * @param user the user that should be removed
     */
    @Override
    public void leave(IUser user) {
        users.remove(user);
    }

    /***
     * This method evaluates whether the given user is a member of the channel or not.
     * @param user The user you want to check if he/she is a member.
     * @return if the user is a member of the channel.
     */
    @Override
    public boolean hasUser(IUser user) {
        return users.contains(user);
    }


    @Override
    public String getDisplayName() {
        return channelProfile.getDisplayName();
    }

    /**
     *
     * @return the id of the channel
     */
    @Override
    public int getID(){
        return channelProfile.getID();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Channel)) return false;
        Channel channel = (Channel) o;
        return this.getID() == channel.getID() && getDisplayName().equals(channel.getDisplayName());

    }

    @Override
    public int hashCode() {

        return Objects.hash(getDisplayName(), getID());
    }

    /**
     *
     * @return The channel's description
     */
    @Override
    public String getDescription() {
        return channelProfile.getDescription();
    }


    /**
     *
     * @return the channels image, null if the channel don't have an image
     */
    @Override
    public Image getDisplayImage() {
        return channelProfile.getDisplayImage();
    }
}
