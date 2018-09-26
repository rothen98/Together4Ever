import controllers.LoginController;
import controllers.WackController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Wack extends Application{

    @Override
    public void start(Stage stage) throws Exception {

        LoginController controller = new LoginController();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("wack_login.fxml"));
        loader.setController(controller);

        Parent root = loader.load();
        //Parent root = FXMLLoader.load(getClass().getResource("wack.fxml"));

        Scene scene = new Scene(root, 600, 400);

        stage.setTitle("wack");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}
