package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import model.IMessage;

import java.io.IOException;
import java.time.LocalDateTime;

public class MessageView extends AnchorPane {

    @FXML
    Label username;
    @FXML
    Label messageText;
    @FXML
    Label timeStamp;
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
        LocalDateTime time = iMessage.getTimestamp();
        String displayedTime = time.getDayOfMonth() + "/" + time.getMonthValue() + " " + time.getHour() + ":" + time.getMinute();
        timeStamp.setText(displayedTime);


    }
}
