package model;

import java.util.Collection;


public interface IServer {
    Collection<IChannel> getUsersChannels(IUser user);
    Collection<String> getChannelNames();
    IChannel getChannel(String name);
    IChannel createNewChannel(String name);
    IUser getUser(String name, String password);
    void addUser(IUser user);





}
