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

    @FXML
    public void signupUsernameKeyPressed(KeyEvent event) {
        //Needs to be improved/shortened
        if (event.getCode() == KeyCode.ENTER) {
            signupPassword.requestFocus();
        } else if (event.getCode() == KeyCode.DOWN) {
            signupPassword.requestFocus();
        } else if (event.getCode() == KeyCode.TAB) {
            signupPassword.requestFocus();
        }
    }

    @FXML
    public void signupKeyPressed(KeyEvent event) {
        //Needs to be improved/shortened
        if (event.getCode() == KeyCode.ENTER) {
            signupButtonPressed();
        } else if (event.getCode() == KeyCode.UP) {
            signupUsername.requestFocus();
        } else if (event.getCode() == KeyCode.DOWN) {
            signupButton.requestFocus();
        }
    }

    @FXML
    public void signupButtonPressed() {
        if (signupUsernameNotEmpty() && signupPasswordNotEmpty()) {
            chatFacade.createUser(getSignupUsername(), getSignupPassword());
            System.out.println("User created with name " + getSignupUsername()
                    + " and password " + getSignupPassword());
        } else {
            System.out.println("Please enter a username and password");
        }
    }

    private boolean signupUsernameNotEmpty() {
        if (signupUsername.getCharacters().length() > 0) {
            return true;
        } else {
            return false;
        }
    }

    private boolean signupPasswordNotEmpty() {
        if (signupPassword.getCharacters().length() > 0) {
            return true;
        } else {
            return false;
        }
    }

    private String getSignupUsername() {
        CharSequence signupUsernameInput = signupUsername.getCharacters();
        return signupUsernameInput.toString();
    }

    private String getSignupPassword() {
        CharSequence signupPasswordInput = signupPassword.getCharacters();
        return signupPasswordInput.toString();
    }


    @FXML
    private void createClient() {
        WackController controller = new WackController(chatFacade, null);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/wack.fxml"));
        loader.setController(controller);

        Stage stage = new Stage();

        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Parent root = FXMLLoader.load(getClass().getResource("wack.fxml"));

        Scene scene = new Scene(root, 1000, 600);

        stage.setTitle("wack");
        stage.setScene(scene);
        stage.show();
    }
}
