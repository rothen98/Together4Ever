package controllers;

import model.ChatFacade;
import model.chatcomponents.channel.IChannel;
import model.chatcomponents.message.IMessage;
import model.chatcomponents.message.MessageType;
import model.identifiers.IIdentifiable;
import model.server.NoChannelFoundException;
import views.IChannelItemHolder;
import views.IChannelListItem;
import views.IChannelListItemController;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

public class ChannelItemHolderController {
    private IChannelListItem selected;
    private LinkedHashMap<IChannelListItemController,IChannelListItem> items;
    private IChannelItemHolder view;

    private ChatFacade facade;


    public ChannelItemHolderController(IChannelItemHolder view) {
        items = new LinkedHashMap<>();
        this.view = view;
        this.facade = facade;
    }

    public void addChannelListItem(IChannelListItemController controller, IChannelListItem item, ChatFacade facade){
        items.put(controller,item);
        this.facade = facade;
    }

    public void arrange(){
        view.clear();
        items = items.entrySet().stream().sorted(Collections.reverseOrder(new Comparator<Map.Entry<IChannelListItemController,IChannelListItem>>() {
            @Override
            public int compare(Map.Entry<IChannelListItemController,IChannelListItem> o1, Map.Entry<IChannelListItemController,IChannelListItem> o2) {
                return o1.getKey().timeOfLatestUpdate().compareTo(o2.getKey().timeOfLatestUpdate());
            }

        })).collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                    LinkedHashMap::new));

        view.update(items.values());
    }
    /**
     * This method will make the given channellistitem "Selected" by giving it a
     * color which differentiates it from the others.
     *
     * @param item the item that should be selected
     */
    private void selectChannelListItem(IChannelListItem item) {
        if (selected != null) {
            selected.getNode().setStyle("");
        }
        if (item != null) {
            selected = item;
            item.getNode().setStyle("-fx-background-color: rgba(41,227,255,0.15);"); // Should be changed...
        }
    }

    public void selectChannel(int id){
        if(id>=0){
            selectChannelListItem(getItem(id));
        }else{
            selectChannelListItem(null);
        }
    }


    public void remove(int id) {
        IChannelListItemController controller = getItemController(id);
        if(controller != null){
            items.remove(controller);
        }
    }

    public boolean contains(int id) {
        IChannelListItemController controller = getItemController(id);
        return controller != null;
    }

    public void addNotificationTo(int id) {
        IChannelListItem item = getItem(id);
        if(item != null){
            item.addNotification();
        }
    }

    private IChannelListItem getItem(int id){
        for(IChannelListItemController item: items.keySet()){
            if(item.getChannelID() == id){
                return items.get(item);
            }
        }
        return null;
    }

    private IChannelListItemController getItemController(int id){
        for(IChannelListItemController item: items.keySet()){
            if(item.getChannelID() == id){
                return item;
            }
        }
        return null;
    }

    public void update(IIdentifiable iIdentifiable) {
        try {
            IChannel updatedChannel = facade.getChannel(iIdentifiable.getID());
            IMessage message = updatedChannel.getLastMessages(1).get(0);
            if(message.getType() == MessageType.KICK){
                remove(iIdentifiable.getID());
            }else{
                 IChannelListItem item = getItem(iIdentifiable.getID());
                 if(item!=null){
                     item.addNotification();
                 }
            }

        } catch (NoChannelFoundException e) {
            remove(iIdentifiable.getID());
        }
            //If there is something wrong here we shouldn't do anything
    }



    /*public void update(IIdentifiable channel) {
        try {
            IChannel updatedChannel = facade.getChannel(channel.getID());
            IMessage message = u
        } catch (NoChannelFoundException e) {
            //If there is something wrong here we shouldn't do anything

        }

    }*/
}
