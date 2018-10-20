package model.chatcomponents.user;

import model.client.IClient;
import model.identifiers.IIdentifiable;
import model.identifiers.IRecognizable;

public interface IUser extends IRecognizable {
    void connectClient(IClient client, String password);
    void removeClient(IClient client);
    void updateClients(IIdentifiable iIdentifiable);
    boolean authorizeLogIn(String password);
    String getName();
    int getAmountOfClients();
    String getHashedPassword();


}
