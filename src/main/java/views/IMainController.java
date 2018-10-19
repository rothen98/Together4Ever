package views;

import model.chatcomponents.channel.IChannel;
import model.identifiers.IIdentifiable;

import java.util.Collection;

public interface IMainController {


    Collection<ISearchItemView> search(String search);

    void newChannel(String channelNameText, String channelDescriptionText);
}
