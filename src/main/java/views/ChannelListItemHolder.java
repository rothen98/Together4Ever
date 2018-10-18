package views;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

public class ChannelListItemHolder extends VBox implements IChannelItemHolder {

    private List<IChannelListItem> items;

    public ChannelListItemHolder() {
        items = new ArrayList<>();
    }


    @Override
    public void update(Collection<IChannelListItem> items) {
        this.items.addAll(items);
    }

    @Override
    public void clear() {
        items.clear();
    }

    @Override
    public Node getNode() {
        return this;
    }
}
