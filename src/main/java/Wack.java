import controllers.LoginController;
import controllers.WackController;
import datahandler.DataHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.ChatFacade;

public class Wack extends Application{
    private static ChatFacade chatFacade;
    @Override
    public void start(Stage stage) throws Exception {
        chatFacade = new ChatFacade(new DataHandler());
        LoginController controller = new LoginController(chatFacade);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("wack_login.fxml"));
        loader.setController(controller);

        Parent root = loader.load();
        //Parent root = FXMLLoader.load(getClass().getResource("wack.fxml"));

        Scene scene = new Scene(root, 600, 400);

        stage.setTitle("wack");
        stage.setScene(scene);
        stage.getIcons().add(new Image("wack_logo.png"));
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                chatFacade.saveAllData();
            }
        }));
    }

}
