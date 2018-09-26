import controllers.WackController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Wack extends Application{

    @Override
    public void start(Stage stage) throws Exception {

        WackController controller = new WackController();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("wack.fxml"));
        loader.setController(controller);

        Parent root = loader.load();
        //Parent root = FXMLLoader.load(getClass().getResource("wack.fxml"));

        Scene scene = new Scene(root, 1000, 600);

        stage.setTitle("wack");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}
