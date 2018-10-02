package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import model.IChannel;

import java.io.IOException;

public class ChannelListItem extends AnchorPane {

    private final IChannel channel;
    private final WackController parentController;

    @FXML
    private Label channelName;
    @FXML
    private Label channelDescription;

    public ChannelListItem(IChannel channel, WackController parentController) {

        this.channel = channel;
        this.parentController = parentController;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/wack_channel_listitem.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        channelName.setText();

    }
}
