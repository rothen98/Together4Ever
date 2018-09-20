package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class Server implements IServer {
    private final Collection<IUser> users;
    private final Collection<IChannel> channels;

    public Server() {
        this.users = new HashSet<>();
        this.channels = new HashSet<>();
    }

    /**
     * This method returns all the channels the given user is a member of.
     * It asks each existing method if it has the given user, and if it does, the channel are
     * added to the collection that are to be returned.
     * @param user This is the user whose channels you want to receive
     * @return A collection with the channels the given user is a member of
     */
    @Override
    public Collection<IChannel> getUserChannels(IUser user) {
        final Collection<IChannel> channelsToReturn = new ArrayList<>();
        channels.forEach(channel -> {
            //TODO
            /*if(channel.hasUser(user)){ //We need to be able to check if a channel has a user
                channelsToReturn.add(channel);
            }*/
        });
        return channelsToReturn;
    }

    /**
     * This method will return all the channels names.
     * @return A collection with the channels names
     */
    @Override
    public Collection<String> getChannelNames() {
        final Collection<String> channelNames = new ArrayList<>();
        channels.forEach(channel -> {
            channelNames.add(channel.getName());
        });
        return channelNames;
    }

    /**
     * This method will check if their is an channel with the given id.
     * If it does, this channel will be returned.
     * @param id
     * @return the channel with the given id
     */
    @Override
    public IChannel getChannel(int id) {
        channels.forEach(channel -> {
            //Todo
            /*if(channel.getID() == id){
                return true;
            }*/
        });
        //throw NoChannelFoundException if no channel found
        return null;
    }


    @Override
    public IUser getUser(String name, String password) {
        users.forEach(user->{
            /*if(user.getName().equals(name)){
                if(user.authorize(password)
                {
                    return user;
                }else{
                 throw WrongPasswordException
                }*/
        });
        //throw NoUserFoundException if user not found
        return null;

    }

    @Override
    public void addChannel(IChannel channel) {
        channels.add(channel);
    }

    @Override
    public void addUser(IUser user) {
        users.add(user);
    }
}
