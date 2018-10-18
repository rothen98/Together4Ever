package controllers;

import model.ChatFacade;
import model.chatcomponents.channel.IChannel;
import model.chatcomponents.message.IMessage;
import model.chatcomponents.message.MessageType;
import model.chatcomponents.user.IUser;
import views.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChannelViewController implements IChannelViewController {

    private IChannel channel;
    private ChatFacade chatFacade;
    private IUser user;
    private IChannelViewParent parentController;
    private IChannelView channelView;

    private int numberOfShowingMessages;

    public ChannelViewController(ChatFacade chatFacade, IUser user, IChannelView channelView,
                                 IChannelViewParent parentController) {
        this.chatFacade = chatFacade;
        this.user = user;
        this.channelView = channelView;
        this.parentController = parentController;
    }

    public int getCurrentChannelID() {
        if (channel != null) {
            return channel.getID();
        } else {
            return -1;
        }
    }

    private boolean senderIsUser(String sender_name) {
        return sender_name.equals(user.getDisplayName());
    }

    @Override
    public void sendMessage(String message) {
        channel.sendMessage(chatFacade.createTextMessage(message, user));
    }


    @Override
    public void update(){
        IMessage message = channel.getLastMessages(1).get(0);
        addNewMessageToChannelView(createMessageView(message));
    }

    private void addNewMessageToChannelView(IMessageView message) {
        channelView.addNewMessage(message);
        numberOfShowingMessages++;
    }
    private void addOldMessageToChannelView(IMessageView message){
        channelView.addOldMessage(message);
        numberOfShowingMessages++;
    }

    @Override
    public void addOldMessages() {

        List<IMessage> messages = channel.getLastMessages(numberOfShowingMessages + 10);
        List<IMessage> oldMessages = messages.subList(0,messages.size() - numberOfShowingMessages);
        Collections.reverse(oldMessages);
        for(IMessage message:oldMessages){
            addOldMessageToChannelView(createMessageView(message));
        }

        if (channel.getAllMessages().size() <= numberOfShowingMessages) {
            channelView.disableLoadingOldMessages();
        }

    }

    @Override
    public void leaveChannel() {
        channel.leave(user);
        parentController.leftChannel(channel);
    }

    @Override
    public void showChannel(IChannel channel) {
        if(channel != null && !channel.equals(this.channel)) {
            this.channel = channel;

            List<IMessageView> messageViews = new ArrayList<>();
            for (IMessage message : channel.getLastMessages(15)) {
                messageViews.add(createMessageView(message));
            }
            numberOfShowingMessages = messageViews.size();
            channelView.setNewChannel(channel.getDisplayName(), messageViews);
            if (channel.getAllMessages().size() > 15) {
                channelView.enableLoadingOldMessages();
            }
        }
    }

    @Override
    public void showNoChannel() {
        channelView.showNoChannel();
    }

    private IMessageView createMessageView(IMessage message){
        if(message.getType()==MessageType.CHANNEL){
            return new ChannelMessageView(message.getSender().getDisplayName(),message.getMessage(),message.getSender().getDisplayImage(),
                    message.getTimestamp(),senderIsUser(message.getSender().getDisplayName()));
        }
        /*else if(message.getType()==MessageType.IMAGE){
            //No implementation yet
        }*/
        else{
            return new MessageView(message.getSender().getDisplayName(),message.getMessage(),message.getSender().getDisplayImage(),
                    message.getTimestamp(),senderIsUser(message.getSender().getDisplayName()));
        }
    }


}
