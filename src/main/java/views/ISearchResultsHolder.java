package views;

import javafx.scene.Node;

import java.util.Collection;

public interface ISearchResultsHolder{

    void addNewResults(Collection<ISearchItemView> result);

    Node getNode();
}
