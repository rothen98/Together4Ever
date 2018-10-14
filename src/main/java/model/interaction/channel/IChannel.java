package model.interaction.channel;

import model.identifiers.IIdentifiable;
import model.identifiers.IRecognizable;
import model.interaction.user.IUser;
import model.interaction.message.IMessage;

import java.util.Collection;
import java.util.List;

public interface IChannel extends IIdentifiable {
    Collection<IRecognizable> getAllUsers();
    List<IMessage> getAllMessages();
    List<IMessage> getLastMessages(int index);
    int getNumberOfUsers();

    void sendMessage(IMessage message);
    void join(IUser user);
    void leave(IUser user);

    boolean hasUser(IUser user);





}
