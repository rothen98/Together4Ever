package model.chatcomponents.message;

import model.identifiers.IRecognizable;

import java.time.LocalDateTime;

public interface IMessage extends IMessageContent {
    IRecognizable getSender();
    LocalDateTime getTimestamp();
    

}
