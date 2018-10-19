package views;

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

    public MemberItem(String membername, boolean isAdmin) {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/wack_member.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.memberName.setText(membername);

        if (isAdmin) {
            kickButton.setVisible(true);
        }

    }

    private void kickButtonPressed() {
        controller.kickUser(memberName.getText());
    }

    private void setController(IMemberItemController controller) {
        this.controller = controller;
    }

    @Override
    public Node getNode() {
        return this;
    }
}
