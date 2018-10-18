package views;

import controllers.MainController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import model.chatcomponents.channel.IChannel;
import model.identifiers.IIdentifiable;

import java.net.URL;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ResourceBundle;

public class MainView implements IMainView {
    @FXML
    AnchorPane mainView;
    @FXML
    AnchorPane newChannelView;
    @FXML
    AnchorPane channelHolder;
    @FXML
    ScrollPane channelListItemScrollPane;
    @FXML
    ScrollPane searchResultsScrollPane;
    @FXML
    TextField searchBar;
    @FXML
    TextField channelName;
    @FXML
    TextField channelDescription;
    @FXML
    Button createGroupButton;
    @FXML
    Button searchButton;
    @FXML
    ImageView closeSearchButton;
    @FXML
    Label channelExistsLabel;

    private IChannelView channelView;
    private IChannelItemHolder channelItemHolder;
    private ISearchResultsHolder searchResultsHolder;
    private IMainController controller;

    public MainView(IChannelView channelView,
                    IChannelItemHolder channelItemHolder, ISearchResultsHolder searchResultsHolder) {
        this.channelView = channelView;
        this.channelItemHolder = channelItemHolder;
        this.searchResultsHolder = searchResultsHolder;
    }
    @Override
    public void setController(IMainController controller){
        this.controller = controller;
    }


    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("init called");
        channelHolder.getChildren().add(channelView.getNode());
        AnchorPane.setBottomAnchor(channelView.getNode(), 0.0);
        AnchorPane.setTopAnchor(channelView.getNode(), 0.0);
        AnchorPane.setLeftAnchor(channelView.getNode(), 0.0);
        AnchorPane.setRightAnchor(channelView.getNode(), 0.0);

        searchBar.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.isEmpty()) {
                    closeSearchButton.setVisible(true);
                } else {
                    closeSearchButton.setVisible(false);
                }
            }
        });

        TextUtility.addTextLimiter(channelName,20);
        TextUtility.addTextLimiter(channelDescription,50);
    }

    /**
     * This method lets the user press Enter on the keyboard to use the searchbar
     *
     * @param event a KeyEvent to check if the user has pressed something on the keyboard
     */
    @FXML
    public void searchbarKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            searchButtonPressed();
        }
    }

    /**
     * This method lets the user use the keyboard to navigate the fields for creating a new channel
     *
     * @param event a KeyEvent to check if the user has pressed something on the keyboard
     */
    @FXML
    public void channelNameKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            channelDescription.requestFocus();
        } else if (event.getCode() == KeyCode.DOWN) {
            channelDescription.requestFocus();
        } else if (event.getCode() == KeyCode.TAB) {
            channelDescription.requestFocus();
        }
    }

    /**
     * This method lets the user use the keyboard to navigate the fields for creating a new channel
     *
     * @param event a KeyEvent to check if the user has pressed something on the keyboard
     */
    @FXML
    public void channelDescriptionKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            createGroupButtonPressed();
        } else if (event.getCode() == KeyCode.DOWN) {
            createGroupButton.requestFocus();
        } else if (event.getCode() == KeyCode.TAB) {
            createGroupButton.requestFocus();
        } else if (event.getCode() == KeyCode.UP) {
            channelName.requestFocus();
        }
    }

    /**
     * This method lets the user press the enter key to create a new channel
     *
     * @param event a KeyEvent to check if the user has pressed something on the keyboard
     */
    @FXML
    public void createGroupButtonKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            createGroupButtonPressed();
        }
    }

    @FXML
    public void searchButtonPressed() {
        if (searchbarNotEmpty()) {
            String searchParameter = searchBar.getCharacters().toString();
            Collection<ISearchItemView> result = controller.search(searchParameter);
            searchResultsHolder.addNewResults(result);
            searchResultsHolder.getNode().toFront();
        }
    }
    @FXML
    private void closeSearch() {
        searchResultsHolder.getNode().toBack();
        closeSearchButton.setVisible(false);
        searchBar.clear();
    }

    /**
     * This method makes sure the user has not left the searchbar empty
     * @return true if the user has typed in something in the searchbar
     */
    private boolean searchbarNotEmpty() {
        return searchBar.getCharacters().length() > 0;
    }

    /**
     * This method closes down the view for creating a new channel if the user clicks on the x button in the
     * top right corner
     */
    @FXML
    public void closeCreateViewButtonPressed() {
        mainView.toFront();
    }

    /**
     * This method closes down the view for creating a new channel if the user clicks on the lightbox surrounding it
     */
    @FXML
    public void closeCreateChannelDetail() {
        mainView.toFront();

    }

    /**
     * This method makes sure the view for creating a new channel doesn't close down if the user clicks on it.
     * It closes down if the user clicks on the grayed out main view behind it.
     *
     * @param event checks if the user has clicked on the mouse
     */
    @FXML
    public void mouseTrap(Event event) {
        event.consume();
    }

    /**
     * This method brings up the view for creating a new channel when the user clicks on Add new group
     */
    @FXML
    public void addGroupButtonPressed() {
        newChannelView.toFront();
    }

    @FXML
    private void createGroupButtonPressed() {
        if (channelnameNotEmpty()) {
            String channelNameText = channelName.getText();
            String channelDescriptionText = channelDescription.getText();
            controller.newChannel(channelNameText,channelDescriptionText);
        }

    }

    @Override
    public void hideCreateChannelView() {
        channelExistsLabel.setVisible(false);
        channelName.clear();
        channelDescription.clear();
        mainView.toFront();
    }

    @Override
    public void showCreateChannelError(String channelNameText) {
        String errorMessage = channelNameText + " already exists";
        channelExistsLabel.setText(errorMessage);
        channelExistsLabel.setVisible(true);

    }

    @Override
    public void resetSearchBar() {
        searchBar.clear();
        closeSearchButton.setVisible(false);
    }

    @Override
    public void showChannelListItemView() {
        channelItemHolder.getNode().toFront();
    }

    /**
     * This method makes sure the user has not left the channel name field empty
     *
     * @return true if the user has typed something in the channel name field
     */
    private boolean channelnameNotEmpty() {
        return channelName.getCharacters().length() > 0;
    }


}
