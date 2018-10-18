package controllers;

import model.chatcomponents.channel.IChannel;
import views.IChannelListItem;
import views.IChannelListItemController;

import java.time.LocalDateTime;

public class ChatListItemController implements IChannelListItemController {
    private final IWackController parentController;
    private final IChannel channel;
    private final IChannelListItem listItem;
    public ChatListItemController(IWackController parentController, IChannel channel, IChannelListItem item) {
        this.parentController = parentController;
        this.channel = channel;
        this.listItem = item;
    }

    @Override
    public void pressed() {
        listItem.removeNotification();
        parentController.openChannelView(channel);
    }

    public LocalDateTime timeOfLastMessage() {
        return channel.getLastMessages(1).get(0).getTimestamp();
    }

    public void update(){
        listItem.addNotification();
    }
}
