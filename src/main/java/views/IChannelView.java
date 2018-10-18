package views;

import javafx.scene.Node;

import java.time.LocalDateTime;
import java.util.List;

public interface IChannelView {

    void addNewTextMessage(String displayName, String displayImage,
                           String message, LocalDateTime timestamp, boolean userOwn);

    void setController(IChannelViewController controller);

    Node getNode();

    void setNewChannel(String displayName, List<IMessageView> messages);

    void showNoChannel();

    void addNewChannelMessage();
}
