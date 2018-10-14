package model.interaction.user;

import model.identifiers.IIdentifiable;
import model.identifiers.IRecognizable;
import model.client.IClient;

public interface IUser extends IRecognizable {
    void connectClient(IClient client, String password);
    void removeClient(IClient client, String password);
    void sendMessageToClients(IIdentifiable iIdentifiable);
    boolean authorizeLogIn(String password);
    String getName();
    int getAmountOfClients();
    String getHashedPassword();


}
