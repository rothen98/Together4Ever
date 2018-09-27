package model;

public interface IClient {

    void updateListeners(IMessage message);
    void addListeners(IClientListener listener);
    void removeListeners(IClientListener listener);
    int getAmountOfListeners();

}
