package model;

import model.client.Client;
import model.identifiers.IIdentifiable;

public class MockClient extends Client {
    private int numberOFMessages;

    public MockClient(){

    }

    @Override
    public void updateListeners(IIdentifiable message) {
        //super.updateClients(message);
        numberOFMessages++;
    }

    public int getAmountOfMessages(){
        return numberOFMessages;
    }
}
