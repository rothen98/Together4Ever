package model;


import java.util.Collection;

public class ChatFacade {
    private IServer server;
    private IClient client;

    public ChatFacade() {

    }

    public IChannel createChannel(String channelName) {

        IChannel channel = new Channel(channelName);
        server.addChannel(channel);
        return channel;
    }

    public Collection<IChannel> getUsersChannel(IUser user) {

        return server.getUserChannels(user);
    }

    public IUser createUSer(String name, String password) {
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

    public void addClientListner() {
        //will do something later inshallah
    }
}
