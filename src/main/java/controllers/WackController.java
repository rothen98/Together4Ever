package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class WackController implements Initializable {

    @FXML Button sendButton;
    @FXML Button searchButton;
    @FXML Button createButton;
    @FXML TextField typeField;
    @FXML TextField searchBar;

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
    public void createButtonPressed() {
        System.out.println("New group created");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}