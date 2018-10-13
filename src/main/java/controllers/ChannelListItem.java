package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import model.IChannel;

import java.io.IOException;
import java.time.LocalDateTime;

public class ChannelListItem extends AnchorPane {

    private final IChannel channel;
    private final WackController parentController;

    @FXML
    private Label channelName;
    @FXML
    private Label channelDescription;
    @FXML
    private Circle notificationCircle;

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

        channelName.setText(channel.getDisplayName());
        channelDescription.setText(channel.getDescription());

    }

    @FXML
    public void onClick() {
        removeNotification();
        parentController.openChannelView(channel);
    }

    private void removeNotification() {
        notificationCircle.setVisible(false);
    }

    public void addNotification() {
        notificationCircle.setVisible(true);
    }

    public LocalDateTime timeOfLastMessage() {
        return channel.getLastMessages(1).get(0).getTimestamp();
    }
}
