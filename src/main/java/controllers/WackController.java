package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class WackController {

    @FXML Button sendButton;

    private void init() {
        
    }

    @FXML
    public void sendButtonPressed() {
        //get data from textfield, check notEmpty and send to flowpane
        System.out.println("sendButton pressed");
    }

}