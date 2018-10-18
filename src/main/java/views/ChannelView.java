package views;

//javafx import
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
//model import
import model.chatcomponents.channel.IChannel;
import model.chatcomponents.message.IMessage;
import model.chatcomponents.message.MessageType;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class ChannelView extends AnchorPane implements IChannelView {

    private IChannelViewController controller;

    private Button loadOldMessagesButton;

    @FXML
    AnchorPane optionsPanel;
    @FXML
    AnchorPane clickBox;
    @FXML
    Button scrollDownButton;
    @FXML
    Label channelName;
    @FXML
    VBox messageList;
    @FXML
    TextField typeField;
    @FXML
    Button sendButton;
    @FXML
    Button optionsButton;
    @FXML
    Button leaveButton;
    @FXML
    ScrollPane messageListScrollPane;


    public ChannelView() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/wack_channelview.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        sendButton.setDisable(true);
        typeField.setDisable(true);
        optionsButton.setVisible(false);
        loadOldMessagesButton = new Button("Load older messages");
        /*loadOldMessagesButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                loadOlderMessages(10);
            }
        });
        setScrollDownAutomatically(true);*/
    }

    public void loadOlderMessages(List<IMessageView> messages) {
        setScrollDownAutomatically(false);
        messageList.getChildren().remove(loadOldMessagesButton);
        loadInMessagesFirst(messages);

        /*int numberOfShowedMessages = messageList.getChildren().size();
        List<IMessage> messages = channel.getLastMessages(numberOfShowedMessages + number);
        int startValue = messages.size() - numberOfShowedMessages - 1;
        for (int i = startValue; i >= 0; i--) {
            showOldMessage(messages.get(i));
            numberOfShowedMessages++;
        }
        if (channel.getAllMessages().size() > numberOfShowedMessages) {
            messageList.getChildren().add(0, loadOldMessagesButton);
        }*/
    }

    public void setNewChannel(String name, List<IMessageView> messageViews){
        readyUp();
        setChannelName(name);
        loadInMessagesLast(messageViews);

    }
    public void showNoChannel(){
        typeField.setDisable(true);
        clickBox.toBack();
        sendButton.setDisable(true);
        optionsButton.setVisible(false);
        scrollDownButton.setVisible(false);
        messageList.getChildren().clear();
        channelName.setText("");
    }

    @Override
    public void addNewChannelMessage() {

    }

    private void readyUp(){
        typeField.setDisable(false);
        clickBox.toBack();
        sendButton.setDisable(false);
        optionsButton.setVisible(true);
        scrollDownButton.setVisible(false);
        setScrollDownAutomatically(true);
        messageList.getChildren().clear();
    }

    public void setChannelName(String name){
        this.channelName.setText(name);
    }

    private void loadInMessagesLast(List<IMessageView> messageViews){
        for (IMessageView messageView:messageViews){
            messageList.getChildren().add(messageView.getNode());
        }
    }
    private void loadInMessagesFirst(List<IMessageView> messageViews){
        for (IMessageView messageView:messageViews){
            messageList.getChildren().add(0, messageView.getNode());
        }
    }

    @FXML
    public void sendButtonKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            sendButtonPressed();
        }
    }

    @FXML
    private void sendButtonPressed() {
        if (messagefieldNotEmpty()) {
            controller.sendMessage(typeField.getText());
            typeField.clear();
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

    private void handleScrollpane() {
        setScrollDownAutomatically(false);
        double viewportmaxy = Math.abs(messageListScrollPane.getViewportBounds().getMinY() -
                messageListScrollPane.getViewportBounds().getHeight());
        double vvalue = messageListScrollPane.getVvalue();

        if (vvalue > 1) {
            vvalue = viewportmaxy / messageList.getHeight();
        }

        if (vvalue < 0.5 && !(messageListScrollPane.getViewportBounds().getMaxY() == messageList.getHeight())) {
            scrollDownButton.setVisible(true);
        } else {
            scrollDownButton.setVisible(false);
            setScrollDownAutomatically(true);
            //setScrollDownAutomatically(true);
        }
    }

    @FXML
    private void optionsMouseTrap(Event event){
        event.consume();
    }

    @FXML
    private void closeOptionsView() {
        clickBox.toBack();
    }

    @FXML
    private void scrollDownButtonPressed() {
        scrollDownButton.setVisible(false);
        slowScrollToBottom(messageListScrollPane);
    }

    @FXML
    private void optionsButtonPressed() {
        clickBox.toFront();
    }

    @FXML
    private void leaveButtonPressed() {
        controller.leaveChannel();
        optionsPanel.toBack();
        sendButton.setDisable(true);
        typeField.setDisable(true);
    }

    private void slowScrollToBottom(ScrollPane scrollPane) {
        Animation animation = new Timeline(
                new KeyFrame(Duration.seconds(0.5),
                        new KeyValue(scrollPane.vvalueProperty(), 1)));
        animation.play();
    }

    private void setScrollDownAutomatically(boolean doIt) {
        if (doIt) {
            messageListScrollPane.vvalueProperty().bind(messageList.heightProperty());
        } else {
            messageListScrollPane.vvalueProperty().unbind();
        }
    }

    @Override
    public void addNewTextMessage(String displayName, String displayImage,
                                  String message, LocalDateTime timestamp,boolean userOwn) {
        handleScrollpane();
        IMessageView messageView = new MessageView(displayName,message,displayImage,timestamp,userOwn);
        messageList.getChildren().add(messageView.getNode());
    }

    @Override
    public void setController(IChannelViewController controller) {
        this.controller = controller;
    }

    @Override
    public Node getNode() {
        return this;
    }
}
