package views;

import controllers.MemberItemController;

import javax.swing.text.html.ImageView;
import java.time.LocalDateTime;

public class ViewComponentsFactory {

    public static ILoginView createLoginView() {
        return new LoginView();
    }

    public static IMainView createMainview(String username, IChannelView channelView,
                                           IChannelItemHolder channelItemHolder, ISearchResultsHolder searchResultsHolder) {
        return new MainView(username, channelView, channelItemHolder, searchResultsHolder);
    }

    public static IChannelView createChannelview() {
        return new ChannelView();
    }

    public static IChannelItemHolder createChannelListItemView() {
        return new ChannelListItemHolder();
    }

    public static ISearchResultsHolder createSearchResultsHolder() {
        return new SearchResultsHolder();
    }

    public static IChannelListItem createChannelListItem(String channelname, String description) {
        return new ChannelListItem(channelname, description);
    }

    public static IMessageView createMessageView(String sendername, String messageText,
                                                 String imageview, LocalDateTime time, boolean usersOwn) {
        return new MessageView(sendername, messageText, imageview, time, usersOwn);
    }

    public static ISearchItemView createSearchItemView(ISearchItemController controller, String name,
                                                       String description, boolean alreadyMember) {
        return new SearchItemView(controller, name, description, alreadyMember);
    }

    public static IMessageView createChannelMessageView(String displayname, String message,
                                                        String displayImage, LocalDateTime time, boolean b) {
        return new ChannelMessageView(displayname, message, displayImage, time, b);
    }

    public static IMemberItem createMemberItem(MemberItemController itemController, String displayName, boolean isAdmin) {
        return new MemberItem(itemController, displayName, isAdmin);
    }
}
