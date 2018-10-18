package views;

import javafx.scene.Node;

import java.time.LocalDateTime;

public interface IChannelView {
    void addNewTextMessage(String displayName, String displayImage, LocalDateTime timestamp);
    void setController(IChannelViewController controller);

    Node getNode();
}
