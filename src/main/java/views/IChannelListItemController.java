package views;

import java.time.LocalDateTime;

public interface IChannelListItemController {
    void pressed();

    LocalDateTime timeOfLatestUpdate();
}
