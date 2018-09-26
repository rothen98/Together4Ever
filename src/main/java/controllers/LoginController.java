package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML TextField loginUsername;
    @FXML TextField loginPassword;
    @FXML TextField signupUsername;
    @FXML TextField signupPassword;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void signupButtonPressed() {
        createClient();
    }

    @FXML
    private void createClient() {
        WackController controller = new WackController();
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
