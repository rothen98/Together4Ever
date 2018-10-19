package controllers;

import model.ChatFacade;
import model.chatcomponents.channel.IChannel;
import model.chatcomponents.user.IUser;
import model.client.IClientListener;
import model.identifiers.IIdentifiable;
import model.server.NoChannelFoundException;
import views.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MainController implements IMainController, IChannelViewParent, ISearchItemParent, IChannelListItemParent, IClientListener {
    private ChannelItemHolderController itemHolderController;
    private ChannelViewController channelViewController;
    private IMainView view;
    private ChatFacade chatFacade;
    private IUser user;

    public MainController(ChatFacade chatFacade, IUser user)  {
        IChannelView channelView = ViewComponentsFactory.createChannelview();
        IChannelItemHolder channelItemHolder = ViewComponentsFactory.createChannelListItemView();
        ISearchResultsHolder searchResultsHolder = ViewComponentsFactory.createSearchResultsHolder();

        channelViewController = new ChannelViewController(chatFacade, user,channelView,this);
        channelView.setController(channelViewController);

        itemHolderController = new ChannelItemHolderController(channelItemHolder);

        view = new MainView(user.getName(),channelView,channelItemHolder,searchResultsHolder);
        view.setController(this);

        this.chatFacade = chatFacade;
        this.user = user;
        Collection<IChannel> channels = chatFacade.getUserChannels(user);
        initChannels(channels);

    }
    /**
     * This method creates a new ChannelListItem for every channel in channels.
     * Updates the ChannelListItemsHolder
     * Opens the channel view with the channel that has the latest message.
     *
     * @param channels The channels to use for the initialize
     */
    public void initChannels(Collection<IChannel> channels) {
        IChannel latest = null;
        for (IChannel channel : channels) {
            addChannelListItem(channel);
            if (latest == null) {
                latest = channel;
            } else {
                int result = latest.getLastMessages(1).get(0).getTimestamp().
                        compareTo(channel.getLastMessages(1).get(0).getTimestamp());
                if (result < 0) {
                    latest = channel;
                }
            }
        }
        if (latest != null) {
            channelViewController.showChannel(latest);
            itemHolderController.selectChannel(latest.getID());
        }
        itemHolderController.arrange();

    }

    @Override
    public Collection<ISearchItemView> search(String search) {
            List<ISearchItemView> listToReturn = new ArrayList<>();
            for (IIdentifiable i : chatFacade.getAllChannels()) {
                if (i.getDisplayName().toLowerCase().contains(search.toLowerCase())) {
                    listToReturn.add(ViewComponentsFactory.createSearchItemView(new SearchItemController(this,i),
                            i.getDisplayName(),i.getDescription(),itemHolderController.contains(i.getID())));
                }
            }
            return listToReturn;

    }

    @Override
    public void newChannel(String channelName, String channelDescription) {
        IChannel newChannel = chatFacade.createChannel(channelName,channelDescription,user);
        if(newChannel != null){
            //addChannelListItem(newChannel);
            channelViewController.showChannel(newChannel);
            itemHolderController.selectChannel(newChannel.getID());
            itemHolderController.arrange();
            view.hideCreateChannelView();
        }else{
            view.showCreateChannelError(channelName);
        }
    }
    public void joinChannel(int id) {
        try {
            IChannel newChannel = chatFacade.getChannel(id);
            addChannelListItem(newChannel);
            channelViewController.showChannel(newChannel);
            itemHolderController.selectChannel(newChannel.getID());
            newChannel.join(user);
            itemHolderController.arrange();
            view.resetSearchBar();
            view.showChannelListItemView();
        } catch (NoChannelFoundException e) {
            e.printStackTrace();
        }
    }

    private void addChannelListItem(IChannel newChannel) {
        IChannelListItem channelListItem = ViewComponentsFactory.createChannelListItem(newChannel.getDisplayName(),newChannel.getDescription());
        IChannelListItemController itemController = new ChatListItemController(this,newChannel,channelListItem);
        channelListItem.setController(itemController);
        itemHolderController.addChannelListItem(itemController,channelListItem);

    }

    public void leftChannel(IChannel channel) {
        itemHolderController.remove(channel.getID());
        itemHolderController.arrange();
        channelViewController.showNoChannel();


    }

    public void update(IIdentifiable iIdentifiable) {
        if (channelViewController.getCurrentChannelID() == iIdentifiable.getID()) {
            channelViewController.update();
        } else if (itemHolderController.contains(iIdentifiable.getID())) {
            itemHolderController.addNotificationTo(iIdentifiable.getID());
        } else {
            try {
                addChannelListItem(chatFacade.getChannel(iIdentifiable.getID()));
            } catch (NoChannelFoundException e) {
                e.printStackTrace();
            }
        }

        itemHolderController.arrange();
    }

    private List<IIdentifiable> getSearchResults(String searchParameter) {
        List<IIdentifiable> listToReturn = new ArrayList<>();
        for (IIdentifiable i : chatFacade.getAllChannels()) {
            if (i.getDisplayName().toLowerCase().contains(searchParameter.toLowerCase())) {
                listToReturn.add(i);
            }
        }
        return listToReturn;
    }

    @Override
    public void openChannelView(IChannel channel) {
        itemHolderController.selectChannel(channel.getID());
        channelViewController.showChannel(channel);
    }

}
