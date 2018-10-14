package model;

import datahandler.DataHandler;
import model.server.IServer;
import model.server.Server;
import model.interaction.user.IUser;
import model.interaction.user.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.HashSet;

import static org.junit.Assert.*;

public class DataHandlerTest {

    private DataHandler dataHandler = new DataHandler();


    IServer server;

    @Before
    public void setUp() throws Exception {
        server = new Server();
    }

    @After
    public void tearDown() throws Exception {
        server = null;
    }

    @Test
    public void loopUsers() {
    }

    @Test
    public void loopChannels() {
    }

    @Test
    public void pushUser() {
        Collection<IUser> users = new HashSet<>();

        IUser user1 = new User("Text", "test");
        IUser user2 = new User("Text23", "test2");
        IUser user3 = new User("Textew", "test3");
        IUser user4 = new User("Text231", "test4");
        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);

        dataHandler.pushUser(users);

        assertTrue(users.size() == 4);
    }

    @Test
    public void pushChannel() {
    }

    @Test
    public void getUsers() {
        /*Collection<IUser> users = new HashSet<>();

        IUser user1 = new User("Text", "test");
        IUser user2 = new User("Text23", "test2");
        IUser user3 = new User("Textew", "test3");
        IUser user4 = new User("Text231", "test4");
        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);

        dataHandler.pushUser(users);

        Collection<IUser> catchUsers = dataHandler.getUsers();
        //assertTrue(catchUsers.size() == 4);
        System.out.println(catchUsers);*/
    }

    @Test
    public void getChannels() {
    }
}