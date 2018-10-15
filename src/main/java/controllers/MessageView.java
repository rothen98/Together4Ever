package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import model.chatcomponents.message.IMessage;

import java.io.IOException;
import java.time.LocalDateTime;

public class MessageView extends AnchorPane {

    @FXML
    private Label username;
    @FXML
    private Label messageText;
    @FXML
    private Label timeStamp;
    @FXML
    private ImageView userPic;
    @FXML
    private AnchorPane backgroundPane;

    public MessageView(IMessage iMessage, boolean clients) {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/wack_message_listitem.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        username.setText(iMessage.getSender().getDisplayName());
        messageText.setText(iMessage.getMessage());
        LocalDateTime time = iMessage.getTimestamp();
        String displayedTime = time.getDayOfMonth() + "/" + time.getMonthValue() + " " + time.getHour() + ":" + time.getMinute();
        timeStamp.setText(displayedTime);

        if(clients){
            backgroundPane.getStyleClass().remove("other-message-background");
            backgroundPane.getStyleClass().add("your-message-background");
        }


    }


}
