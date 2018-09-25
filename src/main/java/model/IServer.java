package model;

import java.util.Collection;


public interface IServer {
    Collection<IChannel> getUserChannels(IUser user);
    Collection<String> getChannelNames();

    IChannel getChannel(int id) throws NoChannelFoundException;
    IUser getUser(String name, String password) throws WrongPasswordException, NoSuchUserFoundException;

    void addChannel(IChannel channel);
    void addUser(IUser user);


}
