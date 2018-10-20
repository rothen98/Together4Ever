package model.server;

import java.util.Collection;

public interface IDataHandler {
    Collection<ChannelData> getChannels();
    Collection<UserData> getUsers();
    void pushChannels(Collection<ChannelData> channels);
    void pushUsers(Collection<UserData> users);
}
