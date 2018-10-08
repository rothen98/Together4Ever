package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import model.IMessage;

import java.io.IOException;

public class MessageItem  {

    IMessage message;

    @FXML
    Label userName;
    @FXML
    Label messageText;

    public MessageItem(IMessage message) {

        this.message = message;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/wack_message_listitem.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        userName.setText(message.getSender().getName());
        messageText.setText(message.getMessageContent().getMessage());
    }
}
