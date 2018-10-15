package model.server;

import model.chatcomponents.channel.IChannel;
import model.chatcomponents.user.IUser;

import java.util.Collection;

public interface IDataHandler {
    Collection<ChannelData> getChannels();
    Collection<IUser> getUsers();
    void pushChannels(Collection<IChannel> channels);
    void pushUsers(Collection<IUser> users);
}
