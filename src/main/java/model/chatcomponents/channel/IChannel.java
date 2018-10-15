package model.chatcomponents.channel;

import model.identifiers.IIdentifiable;
import model.identifiers.IRecognizable;
import model.chatcomponents.user.IUser;
import model.chatcomponents.message.IMessage;

import java.util.Collection;
import java.util.List;

public interface IChannel extends IIdentifiable {
    Collection<IRecognizable> getAllUsersInfo();
    void kick(String userName, String adminPassword);

    List<IMessage> getAllMessages();
    List<IMessage> getLastMessages(int index);
    int getNumberOfUsers();

    void sendMessage(IMessage message);
    void join(IUser user);
    void leave(IUser user);

    boolean hasUser(IUser user);


    boolean isChannelAdministrator(IUser user);
}
