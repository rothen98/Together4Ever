package model;

import java.time.LocalDateTime;

public interface IMessage {
    IUser getSender();
    IMessageContent getMessageContent();
    LocalDateTime getTimestamp();
    

}
