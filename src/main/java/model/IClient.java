package model;

public interface IClient {

    void updateListeners(IIdentifiable message);
    void addListeners(IClientListener listener);
    void removeListeners(IClientListener listener);
    int getAmountOfListeners();

}
