package model;

public interface IUser {
    void connectClient(IClient client, String password);
    void removeClient(IClient client, String password);
    void sendMessageToClients(IMessage message);
    String getName();
    int getAmountOfClients();

}
