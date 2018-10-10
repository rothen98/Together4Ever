package controllers;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import model.*;

import java.net.URL;
import java.util.*;

public class WackController implements Initializable, IClientListener {

    ChatFacade chatFacade;
    IUser user;

    private ChannelView channelView;
    private Map<Integer ,ChannelListItem> channelListItems = new HashMap<>();
    private ChannelListItem selecteChannelItem = null;

    @FXML
    AnchorPane mainView;
    @FXML
    AnchorPane newChannelView;
    @FXML
    AnchorPane channelHolder;
    @FXML
    VBox channelListItemHolder;
    @FXML
    VBox searchResultsHolder;
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


    public WackController(ChatFacade chatFacade, IUser user) {
        this.chatFacade = chatFacade;
        this.user = user;
        channelView = new ChannelView(user,chatFacade);
    }

    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("init called");
        channelHolder.getChildren().add(channelView);
        AnchorPane.setBottomAnchor(channelView,0.0);
        AnchorPane.setTopAnchor(channelView,0.0);
        AnchorPane.setLeftAnchor(channelView,0.0);
        AnchorPane.setRightAnchor(channelView,0.0);
    }

    private void updateChannelList() {
        channelListItemHolder.getChildren().clear();
        for(ChannelListItem c:channelListItems.values()) {
            channelListItemHolder.getChildren().add(c);
        }
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
        String searchParameter;
        searchResultsHolder.getChildren().clear();
        channelListItemHolder.toFront();
        if (searchbarNotEmpty()) {
            searchParameter = searchBar.getCharacters().toString();
            List<IIdentifiable> list = getSearchResults(searchParameter);
            for(IIdentifiable i:list){
                if(channelListItems.containsKey(i.getID())){
                    searchResultsHolder.getChildren().add(new SearchItemView(i,this,true));
                }else{
                    searchResultsHolder.getChildren().add(new SearchItemView(i,this,false));
                }

            }
            searchResultsScrollPane.toFront();
        } else {
            System.out.println("Type what you want to search for");
        }
    }

    private List<IIdentifiable> getSearchResults(String searchParameter) {
        List<IIdentifiable> listToReturn = new ArrayList<>();
        for(IIdentifiable i:chatFacade.getAllChannels()) {
            if(i.getDisplayName().contains(searchParameter)) {
                listToReturn.add(i);
            }
        }
        return listToReturn;
    }

    /**
     * This method makes sure the user has not left the searchbar empty
     *
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
    public void createGroupButtonPressed() {
        //TODO unfinished
        String channelNameText;
        String channelDescriptionText;
        if (channelnameNotEmpty()) {
            channelNameText = channelName.getCharacters().toString();
            channelDescriptionText = channelDescription.getCharacters().toString();
            IChannel createdChannel = chatFacade.createChannel(channelNameText, channelDescriptionText, user);
            channelView.setChannel(createdChannel);
            addChannelListItem(createdChannel);
            updateChannelList();
        } else {
            System.out.println("Type in a group name");
        }
        channelName.clear();
        channelDescription.clear();
        mainView.toFront();
    }

    private void addChannelListItem(IChannel channel){
        ChannelListItem newItem = new ChannelListItem(channel,this);
        channelListItems.put(channel.getID(), newItem);
        selectChannelListItem(newItem);
    }

    /**
     * This method makes sure the user has not left the channel name field empty
     *
     * @return true if the user has typed something in the channel name field
     */
    private boolean channelnameNotEmpty() {
        return channelName.getCharacters().length() > 0;
    }

    public void openChannelView(IChannel channel) {
        channelView.setChannel(channel);
        selectChannelListItem(channelListItems.get(channel.getID()));
    }

    @Override
    public void update(IIdentifiable iIdentifiable) {
        System.out.println("Update!");
        if (channelView.getCurrentChannelID() == iIdentifiable.getID()) {
            channelView.update();
        }
        //channelListItems.get(iIdentifiable.getID()).update();
    }

    public void joinChannel(int id) {
        try {
            IChannel newChannel = chatFacade.getChannel(id);
            newChannel.join(user);
            addChatListItem(newChannel);
            channelView.setChannel(newChannel);
            channelListItemScrollPane.toFront();
            updateChannelList();
        } catch (NoChannelFoundException e) {
            e.printStackTrace();
        }
    }

    private void addChatListItem(IChannel newChannel) {
        ChannelListItem newItem =  new ChannelListItem(newChannel,this);
        channelListItems.put(newChannel.getID(),newItem);
        selectChannelListItem(newItem);
    }

    /**
     * This method will make the given channellistitem "Selected" by giving it a
     * color which differentiates it from the others.
     * @param item the item that should be selected
     */
    private void selectChannelListItem(ChannelListItem item){
        if(selecteChannelItem!= null){
            selecteChannelItem.setStyle("");
        }
        selecteChannelItem = item;
        item.setStyle("-fx-background-color: rgb(209, 230, 230);"); // Should be changed...

    }
}