package model;

import java.time.LocalDateTime;

public class TextMessage extends Message{

    private String textMessage;

    public TextMessage (IUser sender, String text, LocalDateTime timestamp) {
        super(sender, text, timestamp);
    }


    public String getTextMessage() {
        return textMessage;
    }
}
