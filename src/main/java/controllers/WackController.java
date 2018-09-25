package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class WackController implements Initializable {

    @FXML AnchorPane mainView;
    @FXML AnchorPane newChannelView;
    @FXML Button sendButton;
    @FXML Button searchButton;
    @FXML Button addGroupButton;
    @FXML Button createGroupButton;
    @FXML Button closeCreateViewButton;
    @FXML TextField typeField;
    @FXML TextField searchBar;
    @FXML TextField channelName;
    @FXML TextField channelDescription;

    private void init() {
        
    }

    @FXML
    public void sendButtonPressed() {
        //get data from textfield, check notEmpty and send to flowpane
        CharSequence typeFieldCharacters = typeField.getCharacters();
        String message;
        if (typeFieldCharacters.length() > 0) {
            message = typeFieldCharacters.toString();
            System.out.println(message);
            typeField.clear();
        } else {
            System.out.println("Type a message");
        }
    }

    @FXML
    public void searchButtonPressed() {
        CharSequence searchBarCharacters = searchBar.getCharacters();
        String searchParameter;
        if (searchBarCharacters.length() > 0) {
            searchParameter = searchBarCharacters.toString();
            System.out.println("You searched for " + searchParameter);
            searchBar.clear();
        } else {
            System.out.println("Type what you want to search for");
        }
    }

    @FXML
    public void addGroupButtonPressed() {
        newChannelView.toFront();
    }

    @FXML
    public void createGroupButtonPressed() {
        CharSequence channelNameCharacters = channelName.getCharacters();
        CharSequence channelDescriptionCharacters = channelDescription.getCharacters();
        String channelNameText;
        String channelDescriptionText;
        if (channelNameCharacters.length() > 0) {
            channelNameText = channelNameCharacters.toString();
            channelDescriptionText = channelDescriptionCharacters.toString();
            System.out.println("New group " + channelNameText + " created");
            System.out.println("Description: " + channelDescriptionText);
        } else {
            System.out.println("Type in a group name");
        }
        channelName.clear();
        channelDescription.clear();
    }

    @FXML
    public void closeCreateViewButtonPressed() {
        mainView.toFront();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}