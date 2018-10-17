package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import model.chatcomponents.channel.IChannel;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * @author Spondon Siddiqui
 *
 * The ChannelListItem class functions as a controller for the channels that appear in the list beneath
 * the searchbar. It contains methods for adding and removing the circular notification a user receives
 * when they have unread messages and is responsible for setting the correct name and description of
 * a channel.
 */

public class ChannelListItem extends AnchorPane {

    private final IChannel channel;
    private final IWackController parentController;

    @FXML
    private Label channelName;
    @FXML
    private Label channelDescription;

    /**
     * A red circle that shows up whenever the user has unread messages in a channel
     * they are not currently viewing
     */
    @FXML
    private Circle notificationCircle;

    public ChannelListItem(IChannel channel, IWackController parentController) {

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

    /**
     * Opens up the view for the channel the user clicks on. Also removes the notification for
     * said channel
     */
    @FXML
    public void onClick() {
        removeNotification();
        parentController.openChannelView(channel);
    }

    /**
     * Shows the red circle for the user
     */
    public void addNotification() {
        notificationCircle.setVisible(true);
    }

    /**
     * Hides the red circle from the user
     */
    private void removeNotification() {
        notificationCircle.setVisible(false);
    }

    public LocalDateTime timeOfLastMessage() {
        return channel.getLastMessages(1).get(0).getTimestamp();
    }
}
