package views;

//javafx imports

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.time.LocalDateTime;

//model imports

public class MessageView extends AnchorPane implements IMessageView {

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

    public MessageView(String sendername, String messageText, String ImageView, LocalDateTime time, boolean usersOwn) {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/wack_message_listitem.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        username.setText(sendername);
        this.messageText.setText(messageText);
        String displayedTime = time.getDayOfMonth() + "/" + time.getMonthValue() + " " + time.getHour() + ":" + time.getMinute();
        timeStamp.setText(displayedTime);

        if(usersOwn){
            backgroundPane.getStyleClass().remove("other-message-background");
            backgroundPane.getStyleClass().add("your-message-background");
        }

    }


    @Override
    public Node getNode() {
        return this;
    }
}
