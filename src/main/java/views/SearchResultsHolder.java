package views;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.Collection;
import java.util.LinkedHashMap;

public class SearchResultsHolder extends VBox implements ISearchResultsHolder {
    @Override
    public void addNewResults(Collection<ISearchItemView> result) {
        getChildren().clear();
        if (result.isEmpty()) {
            getChildren().add(new Label("No channels found"));
        }
        for(ISearchItemView sw:result){
           result.add(sw.getNode());
       }
    }

    @Override
    public Node getNode() {
        return this;
    }
}
