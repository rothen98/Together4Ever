package model.chatcomponents.message;

import java.time.LocalDateTime;

public interface IMessage extends IMessageContent {
    String getSenderName();
    String getSenderDisplayName();
    String getSenderImagePath();
    LocalDateTime getTimestamp();
    

}
