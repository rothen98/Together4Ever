package controllers;

//javafx imports

import model.ChatFacade;
import model.chatcomponents.user.IUser;
import model.client.IClient;
import model.server.NoSuchUserFoundException;
import model.server.WrongPasswordException;
import services.PasswordEncryption.JBCryptAdapter;
import services.datahandler.DataHandler;
import views.ILoginController;
import views.ILoginView;

//Model imports

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


    private final ILoginView view;

    public LoginController(ILoginView view) {
        this.view = view;
        JBCryptAdapter pwEncryptor = new JBCryptAdapter();
        this.chatFacade = new ChatFacade(new DataHandler(), pwEncryptor);
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
            view.reset();
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
            view.reset();
        } catch (NoSuchUserFoundException | WrongPasswordException e) {
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
