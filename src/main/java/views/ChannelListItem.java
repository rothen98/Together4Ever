package views;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;

import java.io.IOException;


/**
 * @author Spondon Siddiqui
 *
 * The ChannelListItem class functions as a controller for the channels that appear in the list beneath
 * the searchbar. It contains methods for adding and removing the circular notification a user receives
 * when they have unread messages and is responsible for setting the correct name and description of
 * a channel.
 */

public class ChannelListItem extends AnchorPane implements IChannelListItem {

    private IChannelListItemController itemController;

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

    public ChannelListItem(String channelName, String channelDescription) {


        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/wack_channel_listitem.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.channelName.setText(channelName);
        this.channelDescription.setText(channelDescription);
    }

    /**
     * Opens up the view for the channel the user clicks on. Also removes the notification for
     * said channel
     */
    @FXML
    public void onClick() {
        itemController.pressed();
    }


    /**
     * Shows the red circle for the user
     */
    public void removeNotification() {
        notificationCircle.setVisible(false);
    }

    public void addNotification() {
        notificationCircle.setVisible(true);
    }

    @Override
    public void setController(IChannelListItemController itemController) {
        this.itemController = itemController;
    }

    @Override
    public Node getNode() {
        return this;
    }


}
