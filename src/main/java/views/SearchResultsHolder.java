package views;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.Collection;

public class SearchResultsHolder extends AnchorPane implements ISearchResultsHolder {

    @FXML
    private VBox searchResultsVbox;

    public SearchResultsHolder() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/search_results_holder.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addNewResults(Collection<ISearchItemView> result) {
        searchResultsVbox.getChildren().clear();
        if (result.isEmpty()) {
            searchResultsVbox.getChildren().add(new Label("No channels found"));
        }
        for(ISearchItemView sw:result){
           searchResultsVbox.getChildren().add(sw.getNode());
       }
    }

    @Override
    public Node getNode() {
        return this;
    }
}
