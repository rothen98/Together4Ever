package controllers;

import model.ChatFacade;
import model.chatcomponents.channel.IChannel;
import model.chatcomponents.message.IMessage;
import model.chatcomponents.message.MessageType;
import model.chatcomponents.user.IUser;
import model.identifiers.IRecognizable;
import views.*;

import java.util.*;

public class ChannelViewController implements IChannelViewController, IMemberItemParent {

    private IChannel channel;
    private final ChatFacade chatFacade;
    private final IUser user;
    private final IChannelViewParent parentController;
    private final IChannelView channelView;

    private int numberOfShowingMessages;

    private final Map<MemberItemController, IMemberItem> members = new HashMap<>();

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
        return sender_name.equals(user.getName());
    }

    @Override
    public void sendMessage(String message) {
        channel.sendMessage(chatFacade.createTextMessage(message, user));
    }



    public void update(){
        IMessage message = channel.getLastMessages(1).get(0);
        if(message.getType() != MessageType.TEXT){
            updateOptionsPanel();
        }

        if(channel.hasUser(user)){
            addNewMessageToChannelView(createMessageView(message));
        }

        if(message.getType() == MessageType.KICK || message.getType() == MessageType.LEAVE){
            if(!channel.hasUser(user)){
                parentController.leftChannel(channel);
            }else{
                removeFromOptionsPanel(message.getSenderName());
            }
        }

    }

    private void removeFromOptionsPanel(String displayName) {
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

    private void updateOptionsPanel(){
        boolean isAdmin = channel.isChannelAdministrator(user);
        for(IRecognizable user:channel.getAllUsersInfo()){
            if(!membersContains(user.getDisplayName())){
                addNewMemberItem(user.getDisplayName(),isAdmin && !this.user.getDisplayName().equals(user.getDisplayName()));
            }else{
                MemberItemController c = getMemberItemController(user.getDisplayName());
                if (c != null) {

                    c.setAdmin(isAdmin && !this.user.getDisplayName().equals(user.getDisplayName()));
                }
            }
        }
        channelView.updateMembers(members.values());
    }

    private void addNewMemberItem(String displayName, boolean isAdmin) {
        IMemberItem item = ViewComponentsFactory.createMemberItem(displayName,isAdmin);
        MemberItemController controller = new MemberItemController(this,item,displayName);
        item.setController(controller);
        members.put(controller,item);
    }

    private MemberItemController getMemberItemController(String displayName) {
        for(MemberItemController controller:members.keySet()){
            if(controller.getMemberName().equals(displayName)){
                return controller;
            }
        }
        return null;
    }

    private boolean membersContains(String displayName) {
        for(MemberItemController controller:members.keySet()){
            if(controller.getMemberName().equals(displayName)){
                return true;
            }
        }
        return false;
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
    }

    @Override
    public void initMembers() {

        if(members.isEmpty()){
            boolean isAdmin = channel.isChannelAdministrator(user);
            for(IRecognizable user:channel.getAllUsersInfo()){
                addNewMemberItem(user.getDisplayName(),isAdmin && !this.user.getDisplayName().equals(user.getDisplayName()));
            }
        }else{
            updateOptionsPanel();
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
        if(message.getType()==MessageType.JOIN){
            return ViewComponentsFactory.createChannelMessageView(message.getSenderDisplayName(),message.getMessage(),message.getSenderImagePath(),
                    message.getTimestamp(),senderIsUser(message.getSenderName()));
        }else if(message.getType()==MessageType.LEAVE){
            return ViewComponentsFactory.createChannelMessageView(message.getSenderDisplayName(),message.getMessage(),message.getSenderImagePath(),
                    message.getTimestamp(),senderIsUser(message.getSenderName()));
        }else if(message.getType()==MessageType.KICK){
            return ViewComponentsFactory.createChannelMessageView(message.getSenderDisplayName(),message.getMessage(),message.getSenderImagePath(),
                    message.getTimestamp(),senderIsUser(message.getSenderName()));
        }
        /*else if(message.getType()==MessageType.IMAGE){
            //No implementation yet
        }*/
        else{
            return ViewComponentsFactory.createMessageView(message.getSenderDisplayName(),message.getMessage(),message.getSenderImagePath(),
                    message.getTimestamp(),senderIsUser(message.getSenderName()));
        }
    }

    @Override
    public void kickUser(String username) {
        channel.kick(username, user);
    }
}
