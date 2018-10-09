package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.ChatFacade;
import model.IClient;
import model.IUser;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    TextField loginUsername;
    @FXML
    TextField loginPassword;
    @FXML
    TextField signupUsername;
    @FXML
    TextField signupPassword;
    @FXML
    Button signupButton;

    private final ChatFacade chatFacade = new ChatFacade();


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    /**
     * This method lets the user use the keyboard to navigate the signup fields
     *
     * @param event a KeyEvent to check if the user has pressed something on the keyboard
     */
    @FXML
    public void signupUsernameKeyPressed(KeyEvent event) {
        //TODO shorten/improve method
        if (event.getCode() == KeyCode.ENTER) {
            signupPassword.requestFocus();
        } else if (event.getCode() == KeyCode.DOWN) {
            signupPassword.requestFocus();
        } else if (event.getCode() == KeyCode.TAB) {
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
        //TODO shorten/improve method
        //For some reason can't change method name to signupPasswordKeyPressed
        if (event.getCode() == KeyCode.ENTER) {
            signupButtonPressed();
        } else if (event.getCode() == KeyCode.UP) {
            signupUsername.requestFocus();
        } else if (event.getCode() == KeyCode.DOWN) {
            signupButton.requestFocus();
        }
    }

    /**
     * This method makes sure that the fields for typing in username and password aren't empty,
     * and then creates a user with the given name and password.
     */
    @FXML
    public void signupButtonPressed() {
        if (signupUsernameNotEmpty() && signupPasswordNotEmpty()) {
            IUser user = chatFacade.createUser(getSignupUsername(), getSignupPassword());
            IClient client = chatFacade.createClient();
            user.connectClient(client,getSignupPassword());

            createClient(user,client);

            System.out.println("User created with name " + getSignupUsername()
                    + " and password " + getSignupPassword());
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
    private void createClient(IUser user, IClient client) {
        WackController controller = new WackController(chatFacade, user);
        client.addListeners(controller);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/wack.fxml"));
        loader.setController(controller);

        Stage stage = new Stage();
        stage.setMinHeight(450);
        stage.setMinWidth(600);
        stage.setOnHiding(event -> {
            System.out.println("Closing stage!!!");
        });

        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Parent root = FXMLLoader.load(getClass().getResource("wack.fxml"));

        Scene scene = new Scene(root, 1000, 600);

        stage.setTitle("wack (logged in as " + user.getName() + ")");
        stage.setScene(scene);
        stage.show();
    }
}
