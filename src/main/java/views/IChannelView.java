package views;

import javafx.scene.Node;

import java.util.Collection;
import java.util.List;

public interface IChannelView {

    void setController(IChannelViewController controller);

    Node getNode();

    void setNewChannel(String displayName, List<IMessageView> messages);

    void showNoChannel();

    void addNewMessage(IMessageView messageView);

    void addOldMessage(IMessageView messageView);

    void enableLoadingOldMessages();

    void disableLoadingOldMessages();

    void updateMembers(Collection<IMemberItem> values);
}
