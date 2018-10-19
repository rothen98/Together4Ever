package controllers;

import model.ChatFacade;
import model.chatcomponents.channel.IChannel;
import model.chatcomponents.message.IMessage;
import model.chatcomponents.message.MessageType;
import model.chatcomponents.user.IUser;
import model.identifiers.IIdentifiable;
import model.identifiers.IRecognizable;
import views.*;

import java.util.*;

public class ChannelViewController implements IChannelViewController, IMemberItemParent {

    private IChannel channel;
    private ChatFacade chatFacade;
    private IUser user;
    private IChannelViewParent parentController;
    private IChannelView channelView;

    private int numberOfShowingMessages;

    private Map<MemberItemController, IMemberItem> members = new HashMap<>();

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



    public void update(){
        IMessage message = channel.getLastMessages(1).get(0);
        if(message.getType() != MessageType.TEXT){
            updateOptionsPanel(message.getSender().getDisplayName());
        }
        if(message.getType() == MessageType.KICK){
            if(!channel.hasUser(user)){
                parentController.leftChannel(channel);
            }

        }else{
            addNewMessageToChannelView(createMessageView(message));
        }

    }

    private void updateOptionsPanel(String displayName) {
        MemberItemController toRemove = null;
        for(MemberItemController controller:members.keySet()){
            if(controller.getMemberName().equals(displayName)){
                toRemove = controller;
                break;
            }
        }
        if(toRemove != null){
            members.remove(toRemove);
        }
        channelView.updateMembers(members.values());
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
    public void initMembers() {
        if(members.isEmpty()){
            boolean isAdmin = channel.isChannelAdministrator(user);
            for(IRecognizable user:channel.getAllUsersInfo()){
                MemberItemController itemController = new MemberItemController(this,user.getDisplayName());
                IMemberItem item = ViewComponentsFactory.createMemberItem(itemController,user.getDisplayName(),isAdmin);
                members.put(itemController,item);
            }
        }
        channelView.updateMembers(members.values());
    }

    public void showChannel(IChannel channel) {
        if(channel != null && !channel.equals(this.channel)) {
            this.channel = channel;
            members.clear();
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


    public void showNoChannel() {
        channel = null;
        channelView.showNoChannel();
    }

    private IMessageView createMessageView(IMessage message){
        System.out.println(message.getType().toString());
        if(message.getType()==MessageType.JOIN){
            return ViewComponentsFactory.createChannelMessageView(message.getSender().getDisplayName(),message.getMessage(),message.getSender().getDisplayImage(),
                    message.getTimestamp(),senderIsUser(message.getSender().getDisplayName()));
        }else if(message.getType()==MessageType.LEAVE){
            return ViewComponentsFactory.createChannelMessageView(message.getSender().getDisplayName(),message.getMessage(),message.getSender().getDisplayImage(),
                    message.getTimestamp(),senderIsUser(message.getSender().getDisplayName()));
        }
        /*else if(message.getType()==MessageType.IMAGE){
            //No implementation yet
        }*/
        else{
            return ViewComponentsFactory.createMessageView(message.getSender().getDisplayName(),message.getMessage(),message.getSender().getDisplayImage(),
                    message.getTimestamp(),senderIsUser(message.getSender().getDisplayName()));
        }
    }

    @Override
    public void kickUser(String username) {
        channel.kick(username, user);
    }
}
