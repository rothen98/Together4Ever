package views;

import javafx.scene.Node;

public interface IChannelListItem {
    void removeNotification();

    void addNotification();

    void setController(IChannelListItemController itemController);

    Node getNode();
}
