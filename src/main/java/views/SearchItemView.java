package views;

//javafx imports

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

//model imports

public class SearchItemView extends AnchorPane implements ISearchItemView {


    @FXML
    private
    Label channelName;
    @FXML
    private
    Label channelDescription;
    @FXML
    private
    Button joinButton;

    private final ISearchItemController controller;

    public SearchItemView(ISearchItemController controller, String name, String description, boolean alreadyMember) {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/wack_searchresult_item.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.controller = controller;
        this.channelName.setText(name);
        this.channelDescription.setText(description);
        if(alreadyMember){
            joinButton.setVisible(false);
        }

    }

    @FXML
    public void joinButtonPressed() {
        System.out.println("Called");
        controller.joinChannel();
    }

    @Override
    public Node getNode() {
        return this;
    }
}
