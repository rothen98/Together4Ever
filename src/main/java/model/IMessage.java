package model;

import java.time.LocalDateTime;

public interface IMessage {
    IUser getUser();
    IUser getSender();
    String getMessage();
    LocalDateTime getTimestamp();
    

}
