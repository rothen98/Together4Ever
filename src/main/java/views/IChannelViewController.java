package views;

public interface IChannelViewController {
    void sendMessage(String message);

    void addOldMessages();

    void leaveChannel();


    void initMembers();

}
