package views;

import javafx.scene.Node;
import model.chatcomponents.channel.IChannel;

import java.util.List;

public interface IChannelViewController {
    void sendMessage(String message);

    void addOldMessages();

    void leaveChannel();

    void showChannel(IChannel channel);

    void showNoChannel();

    int getCurrentChannelID();

    void update();

}
