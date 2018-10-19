package model.chatcomponents.message;


import model.chatcomponents.user.IUser;

import java.time.LocalDateTime;

/**
 * @author Viktor Franzén
 *
 * A factory used to create different kinds of messages.
 */
public class MessageFactory {


    /**
     * A method to create a text message.
     * @param message The text to be added to the message
     * @param user The author of the message
     * @return The message.
     */
    public static IMessage createTextMessage(String message, IUser user){
        IMessageContent textContent = new MessageContent(message, MessageType.TEXT);
        return new Message(user,textContent);
    }


    /**
     *
     * A method to create a image message.
     * @param path The content to be added to the message. (A string with a filepath or a aldready
     *                created ImageContent.
     * @param user The author of the message
     * @return The message.
     */
    public static IMessage createImageMessage(String path, IUser user){
        IMessageContent imageContent = new MessageContent(path,MessageType.IMAGE);
        return new Message(user,imageContent);
    }



    /**
     * A method to create a join message. (With a standard message).
     * @param user The user that has joined.
     * @return The message.
     */
    public static IMessage createJoinMessage(IUser user){
        String message = user.getName() + " just joined the channel.";
        IMessageContent content = new MessageContent(message, MessageType.JOIN);
        return new Message(user,content);
    }

    /**
     * A method to create a leave message. (With a standard message).
     * @param user The user that has left.
     * @return The message.
     */
    public static IMessage createLeaveMessage(IUser user){
        String message = user.getName() + " just left the channel.";
        IMessageContent content = new MessageContent(message, MessageType.LEAVE);
        return new Message(user,content);
    }

    /**
     * A method to create a kick message. (With a standard message)
     * @param user The user that has been kicked.
     * @return The message.
     */
    public static IMessage createKickMessage(IUser user){
        String message = user.getName() + " has just been kicked from the channel.";
        IMessageContent content = new MessageContent(message,MessageType.KICK);
        return new Message(user,content);
    }


    /**
     * A method that can be used to create a channelmessage with an inputed string.
     * Use when there is no standard message for what the channel are supposed to print.
     * @param text The message text.
     * @param user The user that is going to be the author of the message.
     * @return The message.
     */
    public static IMessage createChannelMessage(String text, IUser user){
        IMessageContent content = new MessageContent(text, MessageType.CHANNEL);
        return new Message(user,content);
    }


    /**
     * Method to create a message with a timestamp and messageContent.
     * @param sender The sender of the message.
     * @param messageContent The content of the message.
     * @param timestamp The timestamp of the message.
     * @return The message.
     */
    public static IMessage createMessageWithTimestamp(IUser sender, IMessageContent messageContent, LocalDateTime timestamp){
        return new Message(sender,messageContent,timestamp);
    }

    /**
     * This method examines the given type and creates the right kind of message.
     * @param sender
     * @param content
     * @param type
     * @param timestamp
     * @return
     */
    public static IMessage createMessageWithTimestamp(IUser sender, String content, MessageType type, LocalDateTime timestamp) {
        if(type.equals(MessageType.IMAGE)){
            return new Message(sender,new MessageContent(content, MessageType.IMAGE),timestamp);
        }else if(type.equals(MessageType.CHANNEL)){
            return new Message(sender,new MessageContent(content, MessageType.CHANNEL),timestamp);
        }else if(type.equals(MessageType.TEXT)){
            return new Message(sender,new MessageContent(content, MessageType.TEXT),timestamp);
        }else if(type.equals(MessageType.KICK)){
            return new Message(sender,new MessageContent(content,MessageType.KICK),timestamp);
        }else if(type.equals(MessageType.JOIN)){
            return new Message(sender,new MessageContent(content,MessageType.JOIN),timestamp);
        }else{
            return new Message(sender,new MessageContent(content,MessageType.LEAVE),timestamp);
        }

    }


    /**
     * Creates a message with the given type. Can be used if the standard messages are not good enough
     * for the current scenario.
     * @param sender The sender of the message.
     * @param content A string with the message.
     * @param type The type of the message.
     * @return The message.
     */
    public static IMessage createMessage(IUser sender, String content, MessageType type) {
        if(type.equals(MessageType.IMAGE)){
            return new Message(sender,new MessageContent(content, MessageType.IMAGE));
        }else if(type.equals(MessageType.CHANNEL)){
            return new Message(sender,new MessageContent(content, MessageType.CHANNEL));
        }else if(type.equals(MessageType.TEXT)){
            return new Message(sender,new MessageContent(content, MessageType.TEXT));
        }else if(type.equals(MessageType.KICK)){
            return new Message(sender,new MessageContent(content,MessageType.KICK));
        }else if(type.equals(MessageType.JOIN)){
            return new Message(sender,new MessageContent(content,MessageType.JOIN));
        }else{
            return new Message(sender,new MessageContent(content,MessageType.LEAVE));
        }

    }
}
