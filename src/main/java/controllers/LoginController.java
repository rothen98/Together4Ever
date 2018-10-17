package controllers;

//javafx imports

import datahandler.DataHandler;
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


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Spondon Siddiqui
 *
 * The LoginController class handles the view for logging in and signing up
 */
public class LoginController implements Initializable {

    @FXML
    TextField loginUsername;
    @FXML
    PasswordField loginPassword;
    @FXML
    Button loginButton;
    @FXML
    TextField signupUsername;
    @FXML
    PasswordField signupPassword;
    @FXML
    Button signupButton;

    /**
     * An error message that shows up when the user has inputted an incorrect username or password
     */
    @FXML
    Label loginErrorText;

    /**
     * An error message that shows up when the user's desired username already exists
     */
    @FXML
    Label signupErrorText;

    /**
     * The maximum number of characters the user is allowed to type into the username fields
     */
    private final int usernameMaxCharacters = 20;

    /**
     * The maximum number of characters the user is allowed to type into the password fields
     */
    private final int passwordMaxCharacters = 20;

    private final ChatFacade chatFacade;

    public LoginController() {
        this.chatFacade = new ChatFacade(new DataHandler());
        //Note, the shutdown hook is not called when using the stop button in intellij.
        //You will need to use the exit button.
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Shut down hook active");
                chatFacade.saveAllData();
            }
        }));


    }

    /**
     * Adds limits to how many characters the user can type into the fields
     */
    private void initTextFields() {
        TextUtility.addTextLimiter(signupUsername, usernameMaxCharacters);
        TextUtility.addTextLimiter(loginUsername, usernameMaxCharacters);
        TextUtility.addTextLimiter(signupPassword, passwordMaxCharacters);
        TextUtility.addTextLimiter(loginPassword, passwordMaxCharacters);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTextFields();
    }

    /**
     * This method lets the user use the keyboard to navigate the signup fields
     *
     * @param event a KeyEvent to check if the user has pressed something on the keyboard
     */
    @FXML
    public void signupUsernameKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.DOWN) {
            signupPassword.requestFocus();
        }
    }

    /**
     * This method lets the user use the keyboard to navigate the signup fields
     *
     * @param event a KeyEvent to check if the user has pressed something on the keyboard
     */
    @FXML
    public void signupKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            signupButtonPressed();
        } else if (event.getCode() == KeyCode.UP) {
            signupUsername.requestFocus();
        } else if (event.getCode() == KeyCode.DOWN) {
            signupButton.requestFocus();
        }
    }

    /**
     * This method lets the user use the keyboard to navigate the login fields
     *
     * @param event a KeyEvent to check if the user has pressed something on the keyboard
     */
    @FXML
    private void loginUsernameKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.DOWN) {
            loginPassword.requestFocus();
        }
    }

    /**
     * This method lets the user use the keyboard to navigate the signup fields
     *
     * @param event a KeyEvent to check if the user has pressed something on the keyboard
     */
    @FXML
    private void loginKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            loginButtonPressed();
        } else if (event.getCode() == KeyCode.UP) {
            loginUsername.requestFocus();
        } else if (event.getCode() == KeyCode.DOWN) {
            loginButton.requestFocus();
        }
    }

    /**
     * This method makes sure that the fields for typing in username and password aren't empty
     * and that the username isn't already taken, and then creates a user with the given name and password.
     */
    @FXML
    private void signupButtonPressed() {
        if (signupUsernameNotEmpty() && signupPasswordNotEmpty()) {
            IUser user = chatFacade.createUser(getSignupUsername(), getSignupPassword());
            if (user != null) {
                signupErrorText.setVisible(false);
                IClient client = chatFacade.createClient();
                user.connectClient(client, getSignupPassword());

                initClient(user, client);

                System.out.println("User created with name " + getSignupUsername()
                        + " and password " + getSignupPassword());
            } else {
                signupErrorText.setText("Username already taken");
                signupErrorText.setVisible(true);
            }

        } else {
            System.out.println("Please enter a username and password");
        }
    }

    /**
     * This method makes sure that the user has not left the username field empty
     *
     * @return true if the user has typed something in the field "username"
     */
    private boolean signupUsernameNotEmpty() {
        return signupUsername.getCharacters().length() > 0;
    }

    /**
     * This method makes sure that the user has not left the password field empty
     *
     * @return true if the user has typed in something in the field password
     */
    private boolean signupPasswordNotEmpty() {
        return signupPassword.getCharacters().length() > 0;
    }

    /**
     * This method returns the characters from the username field with a String
     *
     * @return a String with the username that the user has typed in
     */
    private String getSignupUsername() {
        CharSequence signupUsernameInput = signupUsername.getCharacters();
        return signupUsernameInput.toString();
    }

    /**
     * This method returns the characters from the password field with a String
     *
     * @return a String with the password that the user has typed in
     */
    private String getSignupPassword() {
        CharSequence signupPasswordInput = signupPassword.getCharacters();
        return signupPasswordInput.toString();
    }

    /**
     * This method creates a new client for the user
     *
     * @param user The user who will be logged in and using the client
     */
    @FXML
    private void initClient(IUser user, IClient client) {
        WackController controller = new WackController(chatFacade, user);
        client.addListeners(controller);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/wack.fxml"));
        loader.setController(controller);

        Stage stage = new Stage();
        stage.setMinHeight(450);
        stage.setMinWidth(600);
        stage.setOnHiding(event -> {

        });

        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scene scene = new Scene(root, 1000, 600);

        stage.setTitle("wack (logged in as " + user.getName() + ")");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Let's the user log in with an already existing account. Makes sure that the user hasn't
     * typed in an incorrect username or password. Displays error text if they have
     */
    @FXML
    private void loginButtonPressed() {
        try {
            loginErrorText.setVisible(false);
            IUser user = chatFacade.getUser(loginUsername.getText(), loginPassword.getText());
            IClient client = chatFacade.createClient();
            user.connectClient(client, loginPassword.getText());
            initClient(user, client);
        } catch (NoSuchUserFoundException e) {
            loginErrorText.setText("Username or password incorrect");
            loginErrorText.setVisible(true);
        } catch (WrongPasswordException e) {
            loginErrorText.setText("Username or password incorrect");
            loginErrorText.setVisible(true);
        }


    }
}
