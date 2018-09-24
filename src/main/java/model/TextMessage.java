package model;

import java.time.LocalDateTime;

/**
 * @author Alex Solberg
 *
 * This class is related to the Message class but is specifically designed for text(String) messages
 *
 */

public class TextMessage extends Message{

    private String textMessage;

    public TextMessage (IUser sender, String text, LocalDateTime timestamp) {
        super(sender, text, timestamp);
    }

    /**
     *
     * @return the active text message
     */
    public String getTextMessage() {
        return textMessage;
    }
}
