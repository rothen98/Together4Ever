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
    private AnchorPane optionsPanel;
    @FXML
    private AnchorPane clickBox;
    @FXML
    private Button scrollDownButton;
    @FXML
    private Label channelName;
    @FXML
    private VBox messageList;
    @FXML
    private TextField typeField;
    @FXML
    private Button sendButton;
    @FXML
    private Button optionsButton;
    @FXML
    private Button leaveButton;
    @FXML
    private ScrollPane messageListScrollPane;


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
        loadOldMessagesButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                loadOlderMessages();
            }
        });
        setScrollDownAutomatically(true);
    }

    private void loadOlderMessages() {
        setScrollDownAutomatically(false);
        controller.addOldMessages();
    }

    public void setNewChannel(String name, List<IMessageView> messageViews){
        readyUp();
        setChannelName(name);
        loadInMessagesLast(messageViews);
        scrollDownButton.setVisible(false);

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
    public void addNewMessage(IMessageView messageView) {
        handleScrollpane();
        messageList.getChildren().add(messageView.getNode());
    }

    @Override
    public void addOldMessage(IMessageView messageView){
        //handleScrollpane();
        int index;
        if(messageList.getChildren().contains(loadOldMessagesButton)){
            index = 1;
        }else{
            index = 0;
        }
        messageList.getChildren().add(index,messageView.getNode());

    }

    @Override
    public void enableLoadingOldMessages() {
        messageList.getChildren().add(0,loadOldMessagesButton);
        loadOldMessagesButton.setVisible(true);
        System.out.println("Enabled");

    }

    @Override
    public void disableLoadingOldMessages() {
        messageList.getChildren().remove(loadOldMessagesButton);
        System.out.println("Disabled");
    }


    private void readyUp(){
        messageList.getChildren().clear();
        typeField.setDisable(false);
        clickBox.toBack();
        sendButton.setDisable(false);
        optionsButton.setVisible(true);
        scrollDownButton.setVisible(false);
        setScrollDownAutomatically(true);

    }

    public void setChannelName(String name){
        this.channelName.setText(name);
    }

    private void loadInMessagesLast(List<IMessageView> messageViews){
        for (IMessageView messageView:messageViews){
            addNewMessage(messageView);
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
    public void setController(IChannelViewController controller) {
        this.controller = controller;
    }

    @Override
    public Node getNode() {
        return this;
    }
}
