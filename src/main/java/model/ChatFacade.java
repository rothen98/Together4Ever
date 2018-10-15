package model;


import model.identifiers.IIdentifiable;
import model.chatcomponents.message.TextContent;
import model.server.*;
import model.chatcomponents.user.IUser;
import model.chatcomponents.user.User;
import model.chatcomponents.channel.Channel;
import model.chatcomponents.channel.IChannel;
import model.client.Client;
import model.client.IClient;
import model.client.IClientListener;
import model.chatcomponents.message.IMessage;
import model.chatcomponents.message.ImageContent;
import model.chatcomponents.message.Message;

import java.util.Collection;

/**
 * @author Alex Solberg
 *
 * The ChatFacade is a connection between the applications model and the controller.
 */

public class ChatFacade {
    private final IServer server;
    private IClient client;

    public ChatFacade() {
        this.server = new Server();
    }

    /**
     * This method will return a newly created channel
     * @param channelName is a String which represents the name of the channel which will be created
     * @param creator is the user which is creating the channel
     * @return a newly created channel to the caller from the controller
     */
    public IChannel createChannel(String channelName, String description, IUser creator) { //take in a user

        IChannel channel = new Channel(channelName,description);//constructor takes a user
        server.addChannel(channel);
        channel.join(creator);
        return channel;
    }

    /**
     * This method will get the channels that a given user is active in
     * @param user is a wack user which is active in one or more channels
     * @return an updated list of the channels that a specific user is active in
     */
    public Collection<IChannel> getUserChannels(IUser user) {

        return server.getUserChannels(user);
    }

    /**
     * This method allows the controller to create users in the model.
     * If the given name is already taken, null will be returned.
     * @param name is the name the user wishes to have
     * @param password is the password the user will use for identification
     * @return a user that gets created and then stored on the server
     */
    public IUser createUser(String name, String password) {
        if (!server.getAllUserNames().contains(name)){
            IUser user = new User(name, password);
            server.addUser(user);
            return user;
        }
        return null;

    }

    /**
     * This methods finds a match for a user. Used for identification and authentification
     * @param name the users name on the application
     * @param password the password that the user chose
     * @return a user with the matching name/password
     * @throws NoSuchUserFoundException if the user doesnt exist
     * @throws WrongPasswordException if the password doesnt match the user. Used in login
     */
    public IUser getUser(String name, String password) throws NoSuchUserFoundException, WrongPasswordException {
        return server.getUser(name, password);
    }

    /**
     * This method looks for a given channel by its ID
     * @param id the requested ID for an existing channel
     * @return a channel that matches the ID
     * @throws NoChannelFoundException if ID does not match an existing channel on the server
     */
    public IChannel getChannel(int id) throws NoChannelFoundException {
        return server.getChannel(id);
    }

    /**
     * This method creates a client
     * @return a newly created client
     */
    public IClient createClient() {
        this.client = new Client();
        return client;
    }

    /**
     * This method creates a listener and gives it to the client
     * @param listener is a client listener which will be added
     */
    public void addClientListener(IClientListener listener) {
         client.addListeners(listener);

    }

    /**
     * This method will remove a listener from the client
     * @param listener is the client listener that will be removed
     */
    public void deleteClientListener(IClientListener listener) {
        client.removeListeners(listener);
    }

    /**
     * This method will create a new text message object
     * @param textMessage is the message that the user wants to send
     * @param sender is the user that is sending the newly created message
     * @return the newly created textMessage
     */
    public IMessage createTextMessage(String textMessage, IUser sender) {
        return new Message(sender, new TextContent(textMessage));
    }

    /**
     * This method will create a new image message object
     * @param imageMessage is an image file(path) that the user wants to send
     * @param sender is the user that is sending the newly created message
     * @return the newly created imageMessage
     */
    public IMessage createImageMessage(String imageMessage, IUser sender) {
        return new Message(sender, new ImageContent(imageMessage));
    }

    public Collection<IIdentifiable> getAllChannels(){
        return server.getChannels();
    }

    //Add more methods in this class in order to decrease dependencies.
    //For example sendMessage etc.
}
