package model;

import java.time.LocalDate;
import java.util.*;

/**
 * @author Tobias Lindroth
 *
 * The Channel class contains data about the channel's users and messages.
 * There are methods to get the messages, the users names and also methods for joining and leaving the channel.
 */
public class Channel implements IChannel {
    private Collection<IUser> users;
    private List<IMessage> messages;
    private String name;
    private int id;

    public Channel(String name,int id) {
        this.name = name;
        this.users = new HashSet<>();
        this.messages = new ArrayList<>();


    }

    /**
     * This method will return  all the user names of this channel
     * @return a collection with the users names
     */
    @Override
    public Collection<String> getAllUserNames() {
        Collection<String> returnCollection = new ArrayList<>();
        users.forEach(s ->{
            returnCollection.add(s.getName());
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
     * @param list
     * @param <T>
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

    @Override
    public int getNumberOfUsers() {
        return users.size();
    }

    @Override
    public void sendMessage(IMessage message) {
        messages.add(message);
        for(IUser user: users){
            user.sendMessageToClients(message);
        }
    }

    /**
     * This method will add the user to the channel
     * @param user
     */
    @Override
    public void join(IUser user) {
        users.add(user);
    }

    /**
     * This method will remove the user from the channel
     * @param user
     */
    @Override
    public void leave(IUser user) {
        users.remove(user);
    }


    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getID(){
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Channel)) return false;
        Channel channel = (Channel) o;
        return id == channel.getID() &&
                Objects.equals(getName(), channel.getName());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getName(), id);
    }
}
