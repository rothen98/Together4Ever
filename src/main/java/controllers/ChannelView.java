package controllers;

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

/**
 * @author Spondon Siddiqui
 *
 * The ChannelView class handles the view of a channel. It is responsible for showing the channel's
 * messages, the field for typing, and several buttons.
 */
public class ChannelView extends AnchorPane {

    private IChannel channel;
    private IUser user;
    private ChatFacade chatFacade;
    private IWackController parentcontroller;

    /**
     * An option panel that contains settings for the channel and a button that let's the user
     * leave a channel
     */
    @FXML
    AnchorPane optionsPanel;

    /**
     * A box that functions as a bounding box and let's the user click outside of the options panel
     * to close it down
     */
    @FXML
    AnchorPane clickBox;

    /**
     * A button that shows up when the user scrolls up to the top of the messages in a channel
     */
    @FXML
    private Button loadOldMessagesButton;

    /** A button that shows up when the user has unread in the channel they are currently viewing
     */
    @FXML
    Button scrollDownButton;

    @FXML
    Label channelName;
    /**
     * The main panel that contains the messages
     */
    @FXML
    VBox messageList;

    /**
     * The field where the user types in their message
     */
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


    /**
     * Constructor for opening up new ChannelViews. Makes sure the correct buttons and fields
     * are enabled
     * @param user
     * @param chatFacade
     * @param parentcontroller
     */
    public ChannelView(IUser user, ChatFacade chatFacade, IWackController parentcontroller) {

        this.user = user;
        this.chatFacade = chatFacade;
        this.parentcontroller = parentcontroller;

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
                loadOlderMessages(10);
            }
        });
        setScrollDownAutomatically(true);
    }

    /**
     * A method that loads in older messages in a channel
     *
     * @param number How many more messages that should be loaded
     */
    private void loadOlderMessages(int number) {
        setScrollDownAutomatically(false);
        messageList.getChildren().remove(loadOldMessagesButton);
        int numberOfShowedMessages = messageList.getChildren().size();
        List<IMessage> messages = channel.getLastMessages(numberOfShowedMessages + number);
        int startValue = messages.size() - numberOfShowedMessages - 1;
        for (int i = startValue; i >= 0; i--) {
            showOldMessage(messages.get(i));
            numberOfShowedMessages++;
        }
        if (channel.getAllMessages().size() > numberOfShowedMessages) {
            messageList.getChildren().add(0, loadOldMessagesButton);
        }
    }

    /**
     * Opens up the view for the channel that the user clicks on and showing the latest messages.
     * It is also responsible for enabling and disabling the buttons appropriately
     *
     * @param channel The channel that should be opened
     */
    public void setChannel(IChannel channel) {
        if (channel != null) {
            typeField.setDisable(false);
            clickBox.toBack();
            sendButton.setDisable(false);
            optionsButton.setVisible(true);
            scrollDownButton.setVisible(false);
            setScrollDownAutomatically(true);
            this.channel = channel;
            this.channelName.setText(channel.getDisplayName());
            messageList.getChildren().clear();

            if (channel.getAllMessages().size() > 15) {
                messageList.getChildren().add(loadOldMessagesButton);
                for (IMessage m : channel.getLastMessages(15)) {
                    showMessage(m);
                }
            } else {
                for (IMessage m : channel.getAllMessages()) {
                    showMessage(m);
                }
            }

        } else {
            typeField.setDisable(true);
            clickBox.toBack();
            sendButton.setDisable(true);
            optionsButton.setVisible(false);
            scrollDownButton.setVisible(false);
            messageList.getChildren().clear();
            channelName.setText("");
            //parentcontroller.leftChannel(channel);

        }
    }

    private void showMessage(IMessage message) {
        if (message.getType() == MessageType.TEXT) {
            addTextMessage(message, true);
        } else if (message.getType() == MessageType.CHANNEL) {
            addChannelMessage(message, true);
        }
    }

    private void showOldMessage(IMessage message) {
        if (message.getType() == MessageType.TEXT) {
            addTextMessage(message, false);
        } else if (message.getType() == MessageType.CHANNEL) {
            addChannelMessage(message, false);
        }
    }


    @FXML
    public void sendButtonKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            sendButtonPressed();
        }
    }

    /**
     * Sends the message to the channel for the other members to view
     */
    @FXML
    public void sendButtonPressed() {
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

    /**
     * @return The ID of a channel
     */
    public int getCurrentChannelID() {
        if (channel != null) {
            return channel.getID();
        } else {
            return -1;
        }
    }

    /**
     * Updates the view to show the latest messages in a channel
     */
    public void update() {
        IMessage newMessage = channel.getLastMessages(1).get(0);
        handleScrollpane();
        showMessage(newMessage);


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

    /**
     * A method that ensures that the options panel does not close down when you click on it.
     * Without this it would close down because of the bounding box behind it; clickBox
     * @param event
     */
    @FXML
    private void optionsMouseTrap(Event event) {
        event.consume();
    }

    @FXML
    private void closeOptionsView() {
        clickBox.toBack();
    }

    /**
     * A method that let's the user scroll down to the bottom of the channel by clicking on
     * the scrollDownButton
     */
    @FXML
    private void scrollDownButtonPressed() {
        scrollDownButton.setVisible(false);
        slowScrollToBottom(messageListScrollPane);
    }

    /**
     * Opens up the options panel when the user clicks on the optionsButton
     */
    @FXML
    private void optionsButtonPressed() {
        clickBox.toFront();
    }

    /**
     * Let's the user leave a channel. Disables the field for typing and the send button, as
     * the user is not currently in a channel and should not be able to type or send anything
     */
    @FXML
    private void leaveButtonPressed() {
        optionsPanel.toBack();
        channel.leave(user);
        parentcontroller.leftChannel(channel);
        sendButton.setDisable(true);
        typeField.setDisable(true);
    }

    /**
     * Handles the animation for automatically scrolling down to the bottom of the channel
     * @param scrollPane
     */
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


    private void addChannelMessage(IMessage newMessage, boolean last) {
        if (last) {
            messageList.getChildren().add(new Label(newMessage.getMessage()));
        } else {
            messageList.getChildren().add(0, new Label(newMessage.getMessage()));

        }
    }

    private void addTextMessage(IMessage iMessage, boolean last) {
        if (last) {
            messageList.getChildren().add(new MessageView(iMessage, senderIsUser(iMessage.getSender().getDisplayName())));
        } else {
            messageList.getChildren().add(0, new MessageView(iMessage, senderIsUser(iMessage.getSender().getDisplayName())));
        }
    }

    private boolean senderIsUser(String sender_name) {
        return sender_name.equals(user.getName());
    }
}
