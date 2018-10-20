package views;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ChannelListItemHolder extends AnchorPane implements IChannelItemHolder {

    private List<IChannelListItem> items;

    @FXML
    VBox channelListItemVBox;


    public ChannelListItemHolder() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/channel_list_item_holder.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        items = new ArrayList<>();
    }


    @Override
    public void update(Collection<IChannelListItem> items) {
        this.items.clear();
        this.items.addAll(items);
        channelListItemVBox.getChildren().clear();
        for(IChannelListItem item:items){
            channelListItemVBox.getChildren().add(item.getNode());
        }
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
