package views;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginView extends AnchorPane implements ILoginView{

    @FXML
    private
    TextField loginUsername;
    @FXML
    private
    PasswordField loginPassword;
    @FXML
    private
    Button loginButton;
    @FXML
    private
    TextField signupUsername;
    @FXML
    private
    PasswordField signupPassword;
    @FXML
    private
    Button signupButton;

    /**
     * An error message that shows up when the user has inputted an incorrect username or password
     */
    @FXML
    private
    Label loginErrorText;

    /**
     * An error message that shows up when the user's desired username already exists
     */
    @FXML
    private
    Label signupErrorText;
    private ILoginController controller;

    public LoginView() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/wack_login.fxml"));
        loader.setController(this);
        loader.setRoot(this);

        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Stage stage = new Stage();
        Scene scene = new Scene(this, 600, 400);
        stage.setTitle("wack");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.getIcons().add(new Image("wack_logo.png"));
        stage.show();
    }

    public void setController(ILoginController controller){
        this.controller=controller;
    }

    @Override
    public void reset() {
        loginErrorText.setVisible(false);
        loginUsername.clear();
        loginPassword.clear();
        signupErrorText.setVisible(false);
        signupUsername.clear();
        signupPassword.clear();
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
            controller.signUp(signupUsername.getText(),signupPassword.getText());


        } else {
            System.out.println("Please enter a username and password");
        }
    }



    /**
     * Let's the user log in with an already existing account. Makes sure that the user hasn't
     * typed in an incorrect username or password. Displays error text if they have
     */
    @FXML
    private void loginButtonPressed() {
        if(!loginUsername.getText().isEmpty() && !loginPassword.getText().isEmpty()){
            controller.logIn(loginUsername.getText(),loginPassword.getText());
        }

    }


    /**
     * Adds limits to how many characters the user can type into the fields
     */
    private void initTextFields(int limit) {
        TextUtility.addTextLimiter(signupUsername, limit);
        TextUtility.addTextLimiter(loginUsername, limit);
        TextUtility.addTextLimiter(signupPassword, limit);
        TextUtility.addTextLimiter(loginPassword, limit);
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

    @Override
    public void addTextLimiterToTextFields(int i) {
        initTextFields(i);
    }

    private void hideSignUpError() {
        signupErrorText.setVisible(false);
    }

    @Override
    public void showSignUpError(String username) {
        signupErrorText.setText("Username " + username + " already taken");
        signupErrorText.setVisible(true);
    }


    private void hideLoginError() {
        loginErrorText.setVisible(false);

    }

    @Override
    public void showLoginError() {
        loginErrorText.setText("Username or password incorrect");
        loginErrorText.setVisible(true);
    }


}
