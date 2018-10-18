package views;

import javafx.scene.Node;
import model.chatcomponents.channel.IChannel;

public interface IChannelViewController {
    void sendMessage(String message);
    void leaveChannel();

    void showChannel(IChannel channel);

    void showNoChannel();

    int getCurrentChannelID();

    void update();
}
