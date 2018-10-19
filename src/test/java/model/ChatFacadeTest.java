package model;

import model.chatcomponents.channel.Channel;
import model.chatcomponents.message.MessageFactory;
import model.chatcomponents.message.MessageType;
import model.chatcomponents.user.User;
import model.identifiers.IIdentifiable;
import model.identifiers.IRecognizable;
import services.PasswordEncryption.JBCryptAdapter;
import services.datahandler.DataHandler;
import model.server.*;
import model.chatcomponents.user.IUser;
import model.chatcomponents.channel.IChannel;
import model.client.IClient;
import model.client.IClientListener;
import model.chatcomponents.message.IMessage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

import static org.junit.Assert.*;

public class ChatFacadeTest {

    private ChatFacade facade;
    private IServer server;
    private IUser user;

    @Before
    public void setUp() throws Exception {
        DataHandler d = new DataHandler();
        facade = new ChatFacade(new DataHandler(), new JBCryptAdapter());
        server = new Server(d);
        user = new MockUser("Tubby", "D!nMamma123");
    }

    @After
    public void tearDown() throws Exception {
        facade = null;
    }

    @Test

    public void createChannel() throws NoChannelFoundException {
        String channelName = "This is my channel name";
        IChannel channel = facade.createChannel(channelName, "descriptiiiiiiion",user);
        int id = channel.getID();
        assertEquals(channel, facade.getChannel(id));
    }

    @Test
    public void getUserChannels() {
        IChannel channel = facade.createChannel("channel","descriiiiiiption",user);
        user = facade.createUser("Tubby", "D!nMamma123");
        channel.join(user);
        assertTrue(facade.getUserChannels(user).size() == 1);
        assertTrue(facade.getUserChannels(user).contains(channel));
    }

    @Test
    public void createUser() throws NoSuchUserFoundException, WrongPasswordException {
        String username = "Username";
        String password = "Password";
        IUser user = facade.createUser(username, password);
        IUser serverUser = facade.getUser(username, password);
        assertEquals(user, serverUser);
    }

    @Test
    public void getUser() throws NoSuchUserFoundException, WrongPasswordException {
        String username = "Username";
        String password = "Password";
        IUser user = facade.createUser(username, password);
        IUser user2 = facade.getUser(username, password);
        assertTrue(user == user2);
        assertTrue(facade.getUserChannels(user).size() == 0);
    }

    @Test
    public void getChannel() throws NoChannelFoundException {
        String channelName = "this is my name";
        String description = "This is my description";
        IChannel channel = facade.createChannel(channelName, description, user);
        int id = channel.getID();
        IChannel testChannel = facade.getChannel(id);
        assertEquals(testChannel, channel);
    }

    @Test
    public void createClient() {
        String password = "D!nMamma123";
        IClient client = facade.createClient();
        user.connectClient(client, password);
        assertEquals(1, user.getAmountOfClients());

    }

    @Test
    public void addClientListener() {
        IClient client = facade.createClient();
        IClientListener listener = new MockListener();
        client.addListeners(listener);
        assertEquals(1, client.getAmountOfListeners());
    }

    @Test
    public void deleteClientListener() {
        IClient client = facade.createClient();
        IClientListener listener = new MockListener();
        client.addListeners(listener);
        assertEquals(1, client.getAmountOfListeners());
        client.removeListeners(listener);
        assertEquals(0, client.getAmountOfListeners());
    }

    @Test
    public void createTextMessage() {
        String message = "this is my textMessage";
        IMessage textMessage = facade.createTextMessage(message, user);
        IMessage factoryMessage = MessageFactory.createTextMessage(message,user);

        assertEquals(textMessage.getMessage(),factoryMessage.getMessage());
        assertEquals(textMessage.getSender(),factoryMessage.getSender());



    }

    @Test
    public void createImageMessage() {
        String message = "src/this/that/image.jpg";
        String message2 = "src/this/that/image2.jpg";
        IMessage imageMessage = facade.createImageMessage(message, user);
        IMessage imageMessage2 = facade.createImageMessage(message2, user);
        String channelName = "name";
        String description = "descriotion";
        IChannel channel = facade.createChannel(channelName, description, user);
        channel.sendMessage(imageMessage);
        assertTrue(channel.getAllMessages().size() == 2);
        channel.sendMessage(imageMessage2);
        assertTrue(channel.getAllMessages().size() == 3);
    }

    @Test
    public void getAllChannels() {
        facade.createChannel("football", "I like it", new User("viktor", "franzen"));
        Collection<IIdentifiable> list = facade.getAllChannels();
        assertTrue(list.size() == 1);

    }

    @Test
    public void saveAllData() {
        IUser user1 = new User("Viktor" , "Bad");
        IUser user2 = new User("Alex" , "Good");
        server.addUser(user1);
        server.addUser(user2);

        IChannel channel1 = new Channel("Football", "I love football");
        IChannel channel2 = new Channel("Gaming", "I love Gaming");
        server.addChannel(channel1);
        server.addChannel(channel2);

        facade.saveAllData();

        Collection<String> list = server.getAllUserNames();

        System.out.println(list);
        assertTrue(list.contains(user1.getName()));

    }
}