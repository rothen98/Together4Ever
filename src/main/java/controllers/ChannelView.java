package controllers;

//javafx import
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
//model import
import model.ChatFacade;
import model.chatcomponents.channel.IChannel;
import model.chatcomponents.message.IMessage;
import model.chatcomponents.message.MessageType;
import model.chatcomponents.user.IUser;
import java.io.IOException;
import java.util.List;

public class ChannelView extends AnchorPane {

    private IChannel channel;
    private IUser user;
    private ChatFacade chatFacade;

    private Button loadOldMessagesButton;

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
        loadOldMessagesButton = new Button("Load older messages");
        loadOldMessagesButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                loadOlderMessages(10);
            }
        });
        setScrollDownAutomatically(true);



    }

    private void loadOlderMessages(int number){
        setScrollDownAutomatically(false);
        messageList.getChildren().remove(loadOldMessagesButton);
        int numberOfShowedMessages = messageList.getChildren().size();
        List<IMessage> messages = channel.getLastMessages(numberOfShowedMessages+number);
        int startValue = messages.size()-numberOfShowedMessages-1;
        for (int i = startValue;i>=0;i--){
            showOldMessage(messages.get(i));
            numberOfShowedMessages++;
        }
        if(channel.getAllMessages().size() > numberOfShowedMessages){
            messageList.getChildren().add(0,loadOldMessagesButton);
        }


    }



    public void setChannel(IChannel channel) {
        if (channel != null) {
            sendButton.setDisable(false);
            scrollDownButton.setVisible(false);
            setScrollDownAutomatically(true);
            this.channel = channel;
            this.channelName.setText(channel.getDisplayName());
            messageList.getChildren().clear();

            if(channel.getAllMessages().size()>15){
                messageList.getChildren().add(loadOldMessagesButton);
                for (IMessage m : channel.getLastMessages(15)) {
                    showMessage(m);
                }
            }else{
                for (IMessage m : channel.getAllMessages()) {
                    showMessage(m);
                }
            }

        }
    }

    private void showMessage(IMessage message){
        if(message.getType()== MessageType.TEXT){
            addTextMessage(message,true);
        }else if(message.getType()==MessageType.CHANNEL){
            addChannelMessage(message,true);
        }
    }

    private void showOldMessage(IMessage message) {
        if(message.getType()== MessageType.TEXT){
            addTextMessage(message,false);
        }else if(message.getType()==MessageType.CHANNEL){
            addChannelMessage(message,false);
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
        handleScrollpane();
        showMessage(newMessage);


    }

    private void handleScrollpane() {
        setScrollDownAutomatically(false);

        double viewportmaxy = Math.abs(messageListScrollPane.getViewportBounds().getMinY()-
                messageListScrollPane.getViewportBounds().getHeight());
        double vvalue = messageListScrollPane.getVvalue();

        if(vvalue>1){
            vvalue = viewportmaxy/messageList.getHeight();
        }

        if (vvalue < 0.5 && !(messageListScrollPane.getViewportBounds().getMaxY()== messageList.getHeight())){
            scrollDownButton.setVisible(true);
        }else{
            scrollDownButton.setVisible(false);
            setScrollDownAutomatically(true);
            //setScrollDownAutomatically(true);
        }
    }

    @FXML
    private void scrollDownButtonPressed(){
        scrollDownButton.setVisible(false);
        slowScrollToBottom(messageListScrollPane);

    }

    private void slowScrollToBottom(ScrollPane scrollPane) {
        Animation animation = new Timeline(
                new KeyFrame(Duration.seconds(0.5),
                        new KeyValue(scrollPane.vvalueProperty(), 1)));
        animation.play();
    }

    private void setScrollDownAutomatically(boolean doIt) {
        if(doIt){
            messageListScrollPane.vvalueProperty().bind(messageList.heightProperty());
        }else{
            messageListScrollPane.vvalueProperty().unbind();
        }

    }

    private void addChannelMessage(IMessage newMessage,boolean last) {
        if(last){
            messageList.getChildren().add(new Label(newMessage.getMessage()));
        }else{
            messageList.getChildren().add(0,new Label(newMessage.getMessage()));

        }

    }

    private void addTextMessage(IMessage iMessage,boolean last) {
        if (last){
            messageList.getChildren().add(new MessageView(iMessage,senderIsUser(iMessage.getSender().getDisplayName())));
        }else{
            messageList.getChildren().add(0,new MessageView(iMessage,senderIsUser(iMessage.getSender().getDisplayName())));
        }

    }

    private boolean senderIsUser(String sender_name) {
        return sender_name.equals(user.getName());
    }
}
