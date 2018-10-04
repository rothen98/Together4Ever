package model;

public interface IUser extends IRecognizable {
    void connectClient(IClient client, String password);
    void removeClient(IClient client, String password);
    void sendMessageToClients(IMessage message);
    boolean authorizeLogIn(String password);
    String getName();
    int getAmountOfClients();


}
