package views;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class MemberItem extends AnchorPane implements IMemberItem{

    @FXML
    Label memberName;
    @FXML
    Button kickButton;

    public MemberItem() {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/wack_member.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    @Override
    public Node getNode() {
        return this;
    }
}
