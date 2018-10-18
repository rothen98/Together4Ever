package controllers;
//javafx imports

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
//Model imports
import model.ChatFacade;
import model.chatcomponents.user.IUser;
import model.identifiers.IIdentifiable;
import model.server.NoChannelFoundException;
import model.chatcomponents.channel.IChannel;
import model.client.IClientListener;
import views.*;


import java.net.URL;
import java.util.*;

import static java.util.stream.Collectors.toMap;

public class WackController implements IClientListener {

    private ChatFacade chatFacade;
    private IUser user;

    private IMainController mainController;

    public WackController(ChatFacade chatFacade, IUser user) {
        this.chatFacade = chatFacade;
        this.user = user;






    }



    /**
     * This method examines the given iidentifiable.
     * If the current channel view shows the channel, the channel view is asked to update
     * Else if the channelistitems contains the channel, the channellistitem is asked to show a notification
     * Else we assume that another client of the same user has created or joined a new channel, hence this client
     * needs to create a new channellistitem for the new channel.
     * Lastly, the channelListItems container is updated.
     *
     * @param iIdentifiable
     */
    @Override
    public void update(IIdentifiable iIdentifiable) {
        mainController.update(iIdentifiable);
    }








}