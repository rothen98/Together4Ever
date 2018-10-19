package views;

import javafx.scene.Node;

import java.util.Collection;

public interface IChannelItemHolder {
    void update(Collection<IChannelListItem> items);

    void clear();

    Node getNode();
}
