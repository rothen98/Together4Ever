package controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import model.IChannel;
import model.IUser;

public class OptionsPanel extends AnchorPane {

    IUser user;
    IChannel channel;

    public OptionsPanel(IUser user, IChannel channel) {

        this.user = user;
        this.channel = channel;


        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/wack_options_view.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

    }

    private void leaveButtonPressed() {
        channel.leave(user);
    }
}
