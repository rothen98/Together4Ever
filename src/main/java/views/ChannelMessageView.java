package views;

import javafx.scene.Node;
import javafx.scene.control.Label;

import java.time.LocalDateTime;

public class ChannelMessageView extends Label implements IMessageView {
    public ChannelMessageView(String displayName, String message, String displayImage, LocalDateTime timestamp, boolean b) {
        setText(message);
    }

    @Override
    public Node getNode() {
        return this;
    }
}
