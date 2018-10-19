package views;

import javafx.scene.Node;

public interface IMemberItem {

    Node getNode();

    void showKickButton();

    void hideKickbutton();

    void setController(IMemberItemController controller);
}
