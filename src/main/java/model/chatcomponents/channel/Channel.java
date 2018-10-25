package model.chatcomponents.channel;

import model.chatcomponents.message.IMessage;
import model.chatcomponents.message.MessageFactory;
import model.chatcomponents.user.IUser;
import model.identifiers.IIdentifiable;
import model.identifiers.IRecognizable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Tobias Lindroth
 *
 *
 * The Channel class contains data about the channel's users and messages.
 * There are methods to get the messages, send a message, and also methods for joining and leaving the channel.
 */
public class Channel implements IChannel {
    /**
     * The list with the users that are members of the channel
     */
    private final List<IUser> users;
    /**
     * The list with messages this channel has received
     */
    private final List<IMessage> messages;
    /**
     * The channel admin
     */
    private IUser channelAdministrator;

    /**
     * The channel profile
     */
    private IIdentifiable channelProfile;

    /**
     * This fields ensures that every channel gets an unique id.
     */
    private static final AtomicInteger idCounter = new AtomicInteger(0);

    /**
     * Constructor used when creating new channel
     * @param name        The name of the channel
     * @param description The description of the channel
     */
    public Channel(String name,String description) {
        channelProfile = new ChannelProfile(name,idCounter.getAndIncrement(),description);
        this.users = new ArrayList<>();
        this.messages = new ArrayList<>();
    }

    /**
     * Constructor used when creating channel with information of old channel
     * @param channelName  The name of the channel
     * @param description  The description of the channel
     * @param displayImage The imagepath of the channel
     * @param users        A list with the members of the channels
     * @param messages     A list with the messages of the channel
     */
    public Channel(String channelName, String description, String displayImage, List<IUser> users, List<IMessage> messages) {
        channelProfile = new ChannelProfile(channelName,idCounter.getAndIncrement(), description, displayImage);
        this.users =  users;
        this.messages = messages;
        if(!users.isEmpty()){
            channelAdministrator = users.get(0);
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
                sendMessageWithoutCheck(MessageFactory.createKickMessage(userToLeave));
                userToLeave.updateClients(this);


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
     * This method will return the @param amount latest messages that were sent to the channel.
     * If amount is zero or below, null will be returned.
     * @param amount The numbers of messages you wish to be returned
     * @return A list with messages
     */
    @Override
    public List<IMessage> getLastMessages(int amount) {
        if(amount>=messages.size()){
            return getAllMessages();
        }else if (amount>0){
            return copyOfList(messages.subList(messages.size()-amount,messages.size()));
        }else{
            return null;
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
        if (getUsernames().contains(message.getSenderName())) {
            messages.add(message);
            for (IUser user : users) {
                user.updateClients(this);
            }
        }
    }

    /**
     * Use this method when a message is need to be sent fro someone
     * that is no longer part of the channel. For example a leave message
     * from someone that has just left.
     * @param message The message to be broadcast to all users.
     */
    private void sendMessageWithoutCheck(IMessage message){
        messages.add(message);
        for (IUser user : users) {
            user.updateClients(this);
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
        if(channelAdministrator.equals(user)){
            if(!users.isEmpty()){
                channelAdministrator=users.get(0);
            }else{
                channelAdministrator=null;
            }
        }

        sendMessageWithoutCheck(MessageFactory.createLeaveMessage(user));
        user.updateClients(this);
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
    public boolean isChannelAdministrator(IUser user) {
        return channelAdministrator != null && channelAdministrator.equals(user);
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
     * Returns the path of the channel's image
     * @return the channels image, null if the channel don't have an image
     */
    @Override
    public String getDisplayImage() {
        return channelProfile.getDisplayImage();
    }

    //-------------Utility--------------------------------

    /**
     * This method will retrieve the members names
     * @return The members names
     */
    private List<String> getUsernames() {
        List<String> toBeReturned = new ArrayList<>();
        for (IUser user : users) {
            toBeReturned.add(user.getDisplayName());
        }
        return toBeReturned;
    }

    /**
     * This method will copy a list
     * @param list the list to be copied
     * @param <T> any object
     * @return the copy
     */
    private <T> List<T> copyOfList(List<T> list) {
        return new ArrayList<>(list);
    }
}
