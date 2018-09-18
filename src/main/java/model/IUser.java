package model;

public interface IUser {
    void sendMessageToClients(IMessage message);
    String getName();
}
