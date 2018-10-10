package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ChannelView extends AnchorPane {

    private IChannel channel;
    private IUser user;
    private ChatFacade chatFacade;


    @FXML
    Label channelName;
    @FXML
    VBox messageList;
    @FXML
    TextField typeField;
    @FXML
    Button sendButton;
    @FXML
    ScrollPane messageListScrollPane;

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
        messageListScrollPane.vvalueProperty().bind(messageList.heightProperty());
    }

    public void setChannel(IChannel channel) {
        if (channel != null) {
            sendButton.setDisable(false);
            this.channel = channel;
            this.channelName.setText(channel.getDisplayName());
            messageList.getChildren().clear();
            List<String> messages = new ArrayList<>();
            for (IMessage m : channel.getAllMessages()) {
                showMessage(m);
            }
        }
    }

    private void showMessage(IMessage message){
        if(message.getMessageContent().getType()== MessageType.TEXT){
            addTextMessage(message);
        }else if(message.getMessageContent().getType()==MessageType.CHANNEL){
            addChannelMessage(message);
        }
    }

    @FXML
    public void sendButtonKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            sendButtonPressed();
        }
    }

    @FXML
    public void sendButtonPressed() {
        //get data from textfield, check notEmpty and send to listview
        String message;
        if (messagefieldNotEmpty()) {
            message = typeField.getCharacters().toString();
            channel.sendMessage(chatFacade.createTextMessage(message, user));
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

    public int getCurrentChannelID() {
        if (channel != null) {
            return channel.getID();
        } else {
            return -1;
        }
    }

    public void update() {
        IMessage newMessage = channel.getLastMessages(1).get(0);
        showMessage(newMessage);

    }

    private void addChannelMessage(IMessage newMessage) {
        messageList.getChildren().add(new Label(newMessage.getMessageContent().getMessage()));
    }

    private void addTextMessage(IMessage iMessage) {
        messageList.getChildren().add(new MessageView(iMessage));
    }
}
