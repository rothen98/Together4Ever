package views;

public interface IMainView {
    void hideCreateChannelView();


    void showCreateChannelError(String channelNameText);

    void resetSearchBar();

    void showChannelListItemView();

    void setController(IMainController mainController);
}
