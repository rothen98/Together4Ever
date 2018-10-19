package views;

import javafx.scene.Node;
import model.chatcomponents.channel.IChannel;

import java.util.List;

public interface IChannelViewController {
    void sendMessage(String message);

    void addOldMessages();

    void leaveChannel();


}
