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
     * A method to create a text message. Overloaded so it can either take in a String and
     * then create a TextContent from it or take in an already created TextContent and use that
     * instead.
     * @param content The content to be added to the message
     * @param user The author of the message
     * @return The message.
     */
    public static IMessage createTextMessage(String content, IUser user){
        IMessageContent textContent = new TextContent(content);
        return new Message(user,textContent);
    }
    public static IMessage createTextMessage(TextContent content, IUser user){
        return new Message(user,content);
    }


    /**
     *
     * A method to create a image message. Overloaded so it can either take in a String with a filepath and
     * then create a ImageContent from it or take in an already created ImageContent and use that
     * instead.
     * @param content The content to be added to the message. (A string with a filepath or a aldready
     *                created ImageContent.
     * @param user The author of the message
     * @return The message.
     */
    public static IMessage createImageMessage(String content, IUser user){
        IMessageContent imageContent = new ImageContent(content);
        return new Message(user,imageContent);
    }
    public static IMessage createImageMessage(ImageContent content, IUser user){
        return new Message(user,content);
    }


    /**
     * A method to create a join message. (With a standard message).
     * @param user The user that has joined.
     * @return The message.
     */
    public static IMessage createJoinMessage(IUser user){
        String message = user.getName() + " just joined the channel.";
        IMessageContent content = new ChannelContent(message);
        return new Message(user,content);
    }

    /**
     * A method to create a leave message. (With a standard message).
     * @param user The user that has left.
     * @return The message.
     */
    public static IMessage createLeaveMessage(IUser user){
        String message = user.getName() + " just left the channel.";
        IMessageContent content = new ChannelContent(message);
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
        IMessageContent content = new ChannelContent(text);
        return new Message(user,content);
    }


    /**
     * Method to create a message with a timestamp.
     * @param sender The sender of the message.
     * @param messageContent The content of the message.
     * @param timestamp The timestamp of the message.
     * @return The message.
     */
    public static IMessage createMessageWithTimestamp(IUser sender, IMessageContent messageContent, LocalDateTime timestamp){
        return new Message(sender,messageContent,timestamp);
    }
}
