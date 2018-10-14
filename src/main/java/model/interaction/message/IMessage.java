package model.interaction.message;

import model.identifiers.IRecognizable;

import java.time.LocalDateTime;

public interface IMessage {
    IRecognizable getSender();
    //IMessageContent getMessageContent();

    MessageType getType();

    String getMessage();

    LocalDateTime getTimestamp();
    

}
