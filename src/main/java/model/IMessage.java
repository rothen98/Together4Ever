package model;

import java.time.LocalDateTime;

public interface IMessage {
    IRecognizable getSender();
    IMessageContent getMessageContent();
    LocalDateTime getTimestamp();
    

}
