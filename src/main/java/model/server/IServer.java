package model.server;

import model.chatcomponents.channel.IChannel;
import model.chatcomponents.user.IUser;
import model.identifiers.IIdentifiable;

import java.util.Collection;


public interface IServer {
    Collection<IChannel> getUserChannels(IUser user);
    Collection<IIdentifiable> getChannels();


    IChannel getChannel(int id) throws NoChannelFoundException;
    IUser getUser(String name, String password) throws WrongPasswordException, NoSuchUserFoundException;


    void addChannel(IChannel channel);
    void addUser(IUser user);


    Collection<String> getAllUserNames();

    void saveData();
}
