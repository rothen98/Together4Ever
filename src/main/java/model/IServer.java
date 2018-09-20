package model;

import java.util.Collection;


public interface IServer {
    Collection<IChannel> getUsersChannels(IUser user);
    Collection<String> getChannelNames();

    IChannel getChannel(int id);
    IUser getUser(String name, String password);

    void addChannel(IChannel channel);
    void addUser(IUser user);


}
