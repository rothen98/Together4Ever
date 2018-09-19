package model;

import java.time.LocalDateTime;

public interface IMessage {
    IUser getSender();
    String getMessage();
    LocalDateTime getTimestamp();
    

}
