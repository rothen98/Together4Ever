package model.chatcomponents.channel;

import model.identifiers.IIdentifiable;
import model.identifiers.IRecognizable;
import model.chatcomponents.message.IMessage;
import model.chatcomponents.message.MessageFactory;
import model.chatcomponents.user.IUser;

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
    private final List<IUser> users;
    private final List<IMessage> messages;
    private IUser channelAdministrator;

    private IIdentifiable channelProfile;

    /**
     * This fields ensures that every channel gets an unique id.
     */
    private static final AtomicInteger idCounter = new AtomicInteger(0);

    /**
     * Constructor used when creating new channel
     * @param name
     * @param description
     */
    public Channel(String name,String description) {
        channelProfile = new ChannelProfile(name,idCounter.getAndIncrement(),description);
        this.users = new ArrayList<>();
        this.messages = new ArrayList<>();
    }

    /**
     * Constructor used when creating channel with information of old channel
     * @param channelName
     * @param description
     * @param displayImage
     * @param users
     * @param messages
     */
    public Channel(String channelName, String description, String displayImage, List<IUser> users, List<IMessage> messages) {
        channelProfile = new ChannelProfile(channelName,idCounter.getAndIncrement(), description, displayImage);
        this.users =  users;
        this.messages = messages;
        if(!users.isEmpty()){
            channelAdministrator = users.get(0);
            System.out.println("Channel administrator of " + channelName + ": " + channelAdministrator.getName());
        }
    }
    
    /**
     * This method will return all the users info
     * @return a collection with the users
     */
    @Override
    public Collection<IRecognizable> getAllUsersInfo() {
        return new ArrayList<>(users);
    }

    /**
     * Use this method to kick a member of the group.
     * A reference to the channel admin needs to be provided to make sure the action is
     * allowed.
     * @param userName the name of the user that should be kicked
     * @param admin a reference to the channel admin, to make sure you have permission
     */
    @Override
    public void kick(String userName, IUser admin){
        if(isChannelAdministrator(admin)){
            IUser userToLeave = null;
            for (IUser user:users){
                if(user.getName().equals(userName)){
                    userToLeave = user;
                    break;
                }
            }
            if(userToLeave!= null){
                users.remove(userToLeave);
                sendMessage(MessageFactory.createKickMessage(userToLeave));
                userToLeave.sendMessageToClients(this);


            }

        }
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
        if(amount>=messages.size()){
            return getAllMessages();
        }else{
            return copyOfList(messages.subList(messages.size()-amount,messages.size()));
            //Is copy of list really needed?
        }


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
     * This method sends the given message to all it's users.
     * @param message The message to be broadcast to all users.
     */
    @Override
    public void sendMessage(IMessage message) {
        messages.add(message);
        for(IUser user: users){
            user.sendMessageToClients(this);
        }
    }

    /**
     * This method will add the user to the channel
     * If the user is the first to become a member of the channel,
     * he/she will also become channel admin.
     * @param user the user that are to join the channel
     */
    @Override
    public void join(IUser user) {
        if (user != null && !users.contains(user)){
            if(users.isEmpty()){
                channelAdministrator = user;
            }
            users.add(user);

            sendMessage(MessageFactory.createJoinMessage(user));
        }
    }

    /**
     * This method will remove a user from the channel
     * If the user is the current channel admin, the first user to join
     * the channel will be selected as the new channel admin.
     * @param user the user that should be removed
     */
    @Override
    public void leave(IUser user) {
        users.remove(user);
        sendMessage(MessageFactory.createLeaveMessage(user));
        if(channelAdministrator.equals(user)){
            if(!users.isEmpty()){
                channelAdministrator=users.get(0);
            }else{
                channelAdministrator=null;
            }
        }


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

    /**
     * Checks whether the given user is the channel administrator.
     * @param user the possible channel admin
     * @return true if the user is the channel administrator, false otherwise
     */
    @Override
    public boolean isChannelAdministrator(IUser user){
        return channelAdministrator.equals(user);
    }

    /**
     *
     * @return The name of the channel
     */
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
     * Returns the pah of the channel's image
     * @return the channels image, null if the channel don't have an image
     */
    @Override
    public String getDisplayImage() {
        return channelProfile.getDisplayImage();
    }
}
