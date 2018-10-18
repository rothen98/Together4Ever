package controllers;

import model.ChatFacade;
import model.chatcomponents.channel.IChannel;
import model.chatcomponents.message.IMessage;
import model.chatcomponents.message.MessageType;
import model.chatcomponents.user.IUser;
import views.IChannelView;
import views.IChannelViewController;
import views.IMessageView;

public class ChannelViewController implements IChannelViewController {

    private IChannel channel;
    private ChatFacade chatFacade;
    private IUser user;
    private IWackController parentController;
    private IChannelView channelView;

    public ChannelViewController(ChatFacade chatFacade, IUser user, IChannelView channelView,
                                 IWackController parentController) {
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
        return sender_name.equals(user.getName());
    }

    @Override
    public void sendMessage(String message) {
        channel.sendMessage(chatFacade.createTextMessage(message, user));
    }


    @Override
    public void update(){
        IMessage message = channel.getLastMessages(1).get(0);
        if(message.getType() == MessageType.TEXT){
            channelView.addNewTextMessage(message.getSender().getDisplayName(),message.getSender().getDisplayImage(),
                    message.getTimestamp());
        }
    }
    @Override
    public void leaveChannel() {
        channel.leave(user);
        parentController.leftChannel(channel);
    }

    @Override
    public void showChannel(IChannel channel) {
        //TODO
    }

    @Override
    public void showNoChannel() {
        //TODO
    }

    public void setChannel(IChannel channel){
        this.channel = channel;
        //TODO
    }
}
