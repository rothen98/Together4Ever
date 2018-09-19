package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class Channel implements IChannel {
    private Collection<IUser> users;
    private List<IMessage> messages;
    private String name;

    public Channel(String name) {
        this.name = name;
        this.users = new HashSet<>();
        this.messages = new ArrayList<>();

    }

    /**
     * This method will return  all the user names of this channel
     * @return a collection with strings
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
    public void join(IUser user) {
        users.add(user);
    }

    @Override
    public void leave(IUser user) {
        users.remove(user);
    }

    @Override
    public String getName() {
        return name;
    }
}
