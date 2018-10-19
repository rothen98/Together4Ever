package model.server;

import model.chatcomponents.channel.Channel;
import model.chatcomponents.message.IMessage;
import model.chatcomponents.message.MessageFactory;
import model.chatcomponents.message.MessageType;
import model.chatcomponents.user.User;
import model.identifiers.IIdentifiable;
import model.chatcomponents.user.IUser;
import model.chatcomponents.channel.IChannel;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 * This class contains data about which users and channels that exists.
 * There are methods to:
 * Get a certain user or channel
 * Get all channel names
 * Get all channels a certain user is a member of
 * Add new users and channels
 */
public class Server implements IServer {
    private final Collection<IUser> users;
    private final Collection<IChannel> channels;
    private IDataHandler dataHandler;

    public Server(IDataHandler dataHandler) {
        this.users = new HashSet<>();
        this.channels = new HashSet<>();
        this.dataHandler =dataHandler;
        initServer();
    }

    private void initServer() {
        initUsers();
        initChannels();

        System.out.println("Channels: " );
        for(IChannel channel: channels){
            System.out.println(channel.getDisplayName());
        }
        System.out.println("Users");
        for(IUser user: users){
            System.out.println(user.getName());
        }
        }

    private void initChannels() {
        for(ChannelData data: dataHandler.getChannels()) {
            List<IUser> channelUsers = new ArrayList<>();
            for (String username : data.getUserNames()) {
                for (IUser user : users) {
                    if (username.equals(user.getName())) {
                        channelUsers.add(user);
                        break;
                    }
                }
            }
            List<IMessage> channelMessages = initMessages(data.getMessages());
            channels.add(new Channel(
                    data.getChannelName(),
                    data.getDescription(),
                    data.getImage(),
                    channelUsers,
                    channelMessages
            ));
        }
    }

    private List<IMessage> initMessages(List<MessageData> messageData){
        List<IMessage> channelMessages = new ArrayList<>();
        for(MessageData mdata:messageData) {
            for (IUser user : users) {
                if (mdata.getSenderName().equals(user.getName())) {
                    MessageType type;
                    if(mdata.getType().equals("IMAGE")){
                        type = MessageType.IMAGE;
                    }else if(mdata.getType().equals("JOIN")){
                        type = MessageType.JOIN;
                    }else if(mdata.getType().equals("LEAVE")){
                        type = MessageType.LEAVE;
                    }else if(mdata.getType().equals("KICK")){
                        type = MessageType.KICK;
                    }else{
                        type = MessageType.TEXT;
                    }
                    channelMessages.add(MessageFactory.createMessageWithTimestamp(user,mdata.getContent(),
                            type, LocalDateTime.parse(mdata.getTimeStamp())));
                    break;
                }
            }
        }
        return channelMessages;
    }

    private void initUsers() {
        for(UserData user:dataHandler.getUsers()){

            users.add(new User(
                    user.getUsername(),
                    user.getPassword(),
                    user.getDisplayName(),
                    user.getDisplayImage()
            ));
        }
    }


    /**
     * This method returns all the channels the given user is a member of.
     * It asks each existing method if it has the given user, and if it does, the channel is
     * added to the collection that are to be returned.
     * @param user This is the user whose channels you want to receive
     * @return A collection with the channels the given user is a member of
     */
    @Override
    public Collection<IChannel> getUserChannels(IUser user) {
        final Collection<IChannel> channelsToReturn = new ArrayList<>();
        channels.forEach(channel -> {
            if(channel.hasUser(user)){
                channelsToReturn.add(channel);
            }
        });
        return channelsToReturn;
    }

    /**
     * This method will return all the channels.
     * @return A collection with the channels
     */
    @Override
    public Collection<IIdentifiable> getChannels() {
        final Collection<IIdentifiable> theChannels = new ArrayList<>();
        theChannels.addAll(channels);
        return theChannels;
    }

    /**
     *
     * This method will check if there is a channel with the given id.
     * If it does, this channel will be returned.
     * @param id the id of the channel that should be returned
     * @return the channel with the given id
     * @throws NoChannelFoundException if no channel has the given id
     */
    @Override
    public IChannel getChannel(int id) throws NoChannelFoundException {
        for(IChannel c:channels){
            if(c.getID()==id){
                return c;
            }
        }

        throw new NoChannelFoundException();
    }

    /**
     * Use this method to get a certain user
     * @param name the name of the user
     * @param password the password of the user
     * @return The user with the given name and password
     * @throws WrongPasswordException There is a user with the given name, but the password is wrong
     * @throws NoSuchUserFoundException There is no user with the given name
     */
    @Override
    public IUser getUser(String name, String password) throws WrongPasswordException, NoSuchUserFoundException {
        for(IUser user:users){
            if(user.getName().equals(name)){
                if(user.authorizeLogIn(password)) {
                    return user;
                }else{
                    throw new WrongPasswordException();
                }
            }
        }
        throw new NoSuchUserFoundException();
    }

    /**
     * This method will add the given channel to the server
     * @param channel the channel you want to list at the server
     */

    @Override
    public void addChannel(IChannel channel) {
        channels.add(channel);
    }


    /**
     * This method will add the given user to the server
     * @param user the channel you want to list at the server
     */

    @Override
    public void addUser(IUser user) {
        users.add(user);

    }

    @Override
    public Collection<String> getAllUserNames(){
        List<String> listToReturn = new ArrayList<>();
        for(IUser u:users){
            listToReturn.add(u.getName());
        }
        return listToReturn;
    }

    @Override
    public void saveData() {
        List<UserData> userDataList = new ArrayList<>();
        for(IUser user:users){
            userDataList.add(new UserData(user));
        }
        List<ChannelData> channelDataList = new ArrayList<>();
        for(IChannel channel:channels){
            channelDataList.add(new ChannelData(channel));
        }
        dataHandler.pushUsers(userDataList);
        dataHandler.pushChannels(channelDataList);

    }


}
