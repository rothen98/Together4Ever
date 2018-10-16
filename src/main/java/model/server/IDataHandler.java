package model.server;

import model.chatcomponents.channel.IChannel;
import model.chatcomponents.user.IUser;

import java.util.Collection;

public interface IDataHandler {
    Collection<ChannelData> getChannels();
    Collection<UserData> getUsers();
    void pushChannels(Collection<ChannelData> channels);
    void pushUsers(Collection<UserData> users);
}
