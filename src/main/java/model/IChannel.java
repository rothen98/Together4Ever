package model;

import java.util.Collection;
import java.util.List;

public interface IChannel {
    Collection<String> getAllUserNames();
    List<IMessage> getAllMessages();
    List<IMessage> getLastMessages(int index);

    void sendMessage(IMessage message);
    void join(IUser user);
    void leave(IUser user);

    String getName();




}
