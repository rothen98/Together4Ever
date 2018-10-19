package controllers;

//javafx imports

import services.datahandler.DataHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
//Model imports
import model.ChatFacade;
import model.client.IClient;
import model.server.NoSuchUserFoundException;
import model.server.WrongPasswordException;
import model.chatcomponents.user.IUser;
import views.*;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Spondon Siddiqui
 *
 * The LoginController class handles the view for logging in and signing up
 */
public class LoginController implements ILoginController {

    /**
     * The maximum number of characters the user is allowed to type into the username fields
     */
    private final int usernameMaxCharacters = 20;

    /**
     * The maximum number of characters the user is allowed to type into the password fields
     */
    private final int passwordMaxCharacters = 20;

    private final ChatFacade chatFacade;

    private ILoginView view;

    public LoginController(ILoginView view) {
        this.chatFacade = new ChatFacade(new DataHandler());
        this.view = view;
        //Note, the shutdown hook is not called when using the stop button in intellij.
        //You will need to use the exit button.
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Shut down hook active");
                chatFacade.saveAllData();
            }
        }));

        view.addTextLimiterToTextFields(20);


    }


    @Override
    public void signUp(String username, String password) {
        IUser user = chatFacade.createUser(username,password);
        if (user != null) {
            view.hideSignUpError();
            IClient client = chatFacade.createClient();
            user.connectClient(client, password);
            initClient(user, client);

        } else {
            view.showSignUpError(username);
        }
    }

    @Override
    public void logIn(String username, String password) {
        try {
            IUser user = chatFacade.getUser(username, password);
            IClient client = chatFacade.createClient();
            user.connectClient(client, password);
            initClient(user, client);
            view.hideLoginError();
        } catch (NoSuchUserFoundException e) {
            view.showLoginError();
        } catch (WrongPasswordException e) {
            view.showLoginError();
        }
    }

    /**
     * This method creates a new client for the user
     *
     * @param user The user who will be logged in and using the client
     */
    private void initClient(IUser user, IClient client) {

        MainController controller = new MainController(chatFacade, user);
        client.addListeners(controller);
    }
}
