package model;

import java.util.Collection;
import java.util.List;

public interface IChannel extends IInformative {
    Collection<String> getAllUserNames();
    List<IMessage> getAllMessages();
    List<IMessage> getLastMessages(int index);
    int getNumberOfUsers();

    void sendMessage(IMessage message);
    void join(IUser user);
    void leave(IUser user);

    boolean hasUser(IUser user);





}
