package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import model.IMessage;

import java.io.IOException;

public class MessageView extends AnchorPane {

    @FXML
    Label username;
    @FXML
    Label messageText;
    @FXML
    ImageView userPic;

    public MessageView(IMessage iMessage) {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/wack_message_listitem.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        username.setText(iMessage.getSender().getName());
        messageText.setText(iMessage.getMessageContent().getMessage());



    }
}
