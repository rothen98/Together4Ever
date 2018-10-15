package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import model.identifiers.IIdentifiable;

import java.io.IOException;

public class SearchItemView extends AnchorPane {


    @FXML
    Label channelName;
    @FXML
    Label channelDescription;
    @FXML
    Button joinButton;

    private WackController parentcontroller;

    private IIdentifiable i;

    public SearchItemView(IIdentifiable i, WackController parentcontroller, boolean alreadyMember) {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/wack_searchresult_item.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.parentcontroller = parentcontroller;
        this.i = i;
        this.channelName.setText(i.getDisplayName());
        this.channelDescription.setText(i.getDescription());
        if(alreadyMember){
            joinButton.setVisible(false);
        }

    }

    @FXML
    public void joinButtonPressed() {
        System.out.println("Called");
        parentcontroller.joinChannel(i.getID());
    }

}
