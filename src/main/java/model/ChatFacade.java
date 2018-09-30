package model;


import java.util.Collection;

/**
 * @author Alex Solberg
 *
 * The ChatFacade is a connection between the applications model and the controller.
 */
public class ChatFacade {
    private final IServer server;
    private IClient client;
    private IMessage message;
    private TextContent textContent;
    private ImageContent imageContent;
    private IUser sender;

    public ChatFacade() {
        this.server = new Server();
    }

    /**
     * This method will return a newly created channel
     * @param channelName is a String which represents the name of the channel which will be created
     * @return a newly created channel to the caller from the controller
     */
    public IChannel createChannel(String channelName) {

        IChannel channel = new Channel(channelName);
        server.addChannel(channel);
        return channel;
    }

    /**
     *
     * @param user is a wack user that will 
     * @return
     */
    public Collection<IChannel> getUserChannels(IUser user) {

        return server.getUserChannels(user);
    }

    public IUser createUser(String name, String password) {
        IUser user = new User(name, password);
        server.addUser(user);
        return user;
    }

    public IUser getUser(String name, String password) throws NoSuchUserFoundException, WrongPasswordException {
        return server.getUser(name, password);
    }

    public IChannel getChannel(int id) throws NoChannelFoundException {
        return server.getChannel(id);
    }

    public IClient createClient() {
        this.client = new Client();
        return client;
    }

    public void addClientListner(IClientListener listener) {
         client.addListeners(listener);

    }

    public void deleteClientListner(IClientListener listner) {
        client.removeListeners(listner);
    }

    public void createTextMessage(String textMessage) {
        textContent = new TextContent(textMessage);
        message = new Message(sender, textContent);
    }

    public void createImageMessage(String imageMessage) {
        imageContent = new ImageContent(imageMessage);
        message = new Message(sender, imageContent);
    }
}
