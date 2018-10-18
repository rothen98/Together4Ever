package views;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
//model import

import java.io.IOException;

public class ChannelListItem extends AnchorPane implements IChannelListItem {

    private IChannelListItemController itemController;

    @FXML
    private Label channelName;
    @FXML
    private Label channelDescription;
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

    @FXML
    public void onClick() {
        itemController.pressed();
    }

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
