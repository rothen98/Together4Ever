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

    @Override
    public List<IMessage> getAllMessages() {
        return copyOfList(messages);
    }

    private List<IMessage> copyOfList(List<IMessage> messages) {
        return messages;
        //TODO make this method
    }

    @Override
    public List<IMessage> getLastMessages(int amount) {
        return messages.subList(messages.size()-amount,messages.size());
        //Todo does this really return a copy?
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
