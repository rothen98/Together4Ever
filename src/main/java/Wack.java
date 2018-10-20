import controllers.LoginController;
import javafx.application.Application;
import javafx.stage.Stage;
import views.ILoginView;
import views.LoginView;

public class Wack extends Application{
    @Override
    public void start(Stage stage) {
        ILoginView loginView = new LoginView();
        LoginController controller = new LoginController(loginView);
        loginView.setController(controller);


    }

    public static void main(String[] args) {
        launch(args);

    }

}
