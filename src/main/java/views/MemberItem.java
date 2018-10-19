package views;

import controllers.MemberItemController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class MemberItem extends AnchorPane implements IMemberItem{

    private IMemberItemController controller;

    @FXML
    Label memberName;
    @FXML
    Button kickButton;

    public MemberItem(MemberItemController controller, String membername, boolean isAdmin) {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/wack_member.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.memberName.setText(membername);
        this.controller = controller;

        if (isAdmin) {
            kickButton.setVisible(true);
        }

    }

    @FXML
    private void kickButtonPressed() {
        controller.kickUser();
    }

    @Override
    public Node getNode() {
        return this;
    }
}
