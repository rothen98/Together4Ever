package views;

import javafx.scene.Node;
import javafx.scene.layout.VBox;

import java.util.Collection;
import java.util.LinkedHashMap;

public interface ISearchResultsHolder{

    void addNewResults(Collection<ISearchItemView> result);

    Node getNode();
}
