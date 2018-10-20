package model.chatcomponents.message;

import model.identifiers.IRecognizable;

import java.time.LocalDateTime;

public interface IMessage extends IMessageContent {
    String getSenderName();
    String getSenderDisplayName();
    String getSenderImagePath();
    LocalDateTime getTimestamp();
    

}
