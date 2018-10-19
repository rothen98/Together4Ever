import controllers.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Wack extends Application{
    @Override
    public void start(Stage stage) throws Exception {
        LoginController controller = new LoginController();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("wack_login.fxml"));
        loader.setController(controller);
        Parent root = loader.load();


        Scene scene = new Scene(root, 600, 400);
        stage.setTitle("wack");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.getIcons().add(new Image("wack_logo.png"));
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);

    }

}
