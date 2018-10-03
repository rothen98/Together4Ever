package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import model.ChatFacade;
import model.IChannel;
import model.IMessage;
import model.IUser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ChannelView extends AnchorPane {

    private IChannel channel;
    private IUser user;
    private ChatFacade chatFacade;

    @FXML
    ListView messageList;
    @FXML
    TextField typeField;
    @FXML
    Button sendButton;

    public ChannelView(IUser user, ChatFacade chatFacade) {

        this.user = user;
        this.chatFacade = chatFacade;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/wack_channelview.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        sendButton.setDisable(true);
    }

    public void setChannel(IChannel channel) {
        if (channel != null) {
            sendButton.setDisable(false);
            this.channel = channel;
            messageList.getItems().clear();
            List<String> messages = new ArrayList<>();
            for (IMessage m : channel.getAllMessages()) {
                messageList.getItems().add(m.getMessageContent().getMessage());
            }
        }
    }

    @FXML
    public void sendButtonPressed() {
        //get data from textfield, check notEmpty and send to listview
        String message;
        if (messagefieldNotEmpty()) {
            message = typeField.getCharacters().toString();
            channel.sendMessage(chatFacade.createTextMessage(message,user));
            System.out.println(channel.getDisplayName() + ": " + message);
            typeField.clear();
        } else {
            System.out.println("Type a message");
        }
    }

    /**
     * This method makes sure the user has not left the message field empty
     *
     * @return true if the user has typed something in the message field
     */
    private boolean messagefieldNotEmpty() {
        return typeField.getCharacters().length() > 0;
    }

    /**
     * This method lets the user press Enter on the keyboard to send a message
     *
     * @param event a KeyEvent to check if the user has pressed something on the keyboard
     */
    @FXML
    public void messageKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            sendButtonPressed();
        }
    }
}
