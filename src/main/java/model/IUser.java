package model;

public interface IUser extends IRecognizable {
    void connectClient(IClient client, String password);
    void removeClient(IClient client, String password);
    void sendMessageToClients(IIdentifiable iIdentifiable);
    boolean authorizeLogIn(String password);
    String getName();
    int getAmountOfClients();


}
