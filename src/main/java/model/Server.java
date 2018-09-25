package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 * This class contains data about which users and channels that exists.
 * There are methods to:
 * Get a certain user or channel
 * Get all channel names
 * Get all channels a certain user is a member of
 * Add new users and channels
 */
public class Server implements IServer {
    private final Collection<IUser> users;
    private final Collection<IChannel> channels;

    public Server() {
        this.users = new HashSet<>();
        this.channels = new HashSet<>();
    }

    /**
     * This method returns all the channels the given user is a member of.
     * It asks each existing method if it has the given user, and if it does, the channel is
     * added to the collection that are to be returned.
     * @param user This is the user whose channels you want to receive
     * @return A collection with the channels the given user is a member of
     */
    @Override
    public Collection<IChannel> getUserChannels(IUser user) {
        final Collection<IChannel> channelsToReturn = new ArrayList<>();
        channels.forEach(channel -> {
            if(channel.hasUser(user)){ //We need to be able to check if a channel has a user
                channelsToReturn.add(channel);
            }
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
     *
     * This method will check if there is a channel with the given id.
     * If it does, this channel will be returned.
     * @param id the id of the channel that should be returned
     * @return the channel with the given id
     * @throws NoChannelFoundException if no channel has the given id
     */
    @Override
    public IChannel getChannel(int id) throws NoChannelFoundException {
        for(IChannel c:channels){
            if(c.getID()==id){
                return c;
            }
        }

        throw new NoChannelFoundException();
    }

    /**
     * Use this method to get a certain user
     * @param name the name of the user
     * @param password the password of the user
     * @return The user with the given name and password
     * @throws WrongPasswordException There is a user with the given name, but the password is wrong
     * @throws NoSuchUserFoundException There is no user with the given name
     */
    @Override
    public IUser getUser(String name, String password) throws WrongPasswordException, NoSuchUserFoundException {
        for(IUser user:users){
            if(user.getName().equals(name)){
                if(user.authorizeLogIn(password)) {
                    return user;
                }else{
                    throw new WrongPasswordException();
                }
            }
        }
        throw new NoSuchUserFoundException();
    }

    /**
     * This method will add the given channel to the server
     * @param channel the channel you want to list at the server
     */

    @Override
    public void addChannel(IChannel channel) {
        channels.add(channel);
    }


    /**
     * This method will add the given user to the server
     * @param user the channel you want to list at the server
     */

    @Override
    public void addUser(IUser user) {
        users.add(user);
    }
}
