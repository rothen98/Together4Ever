package model;

import model.identifiers.IIdentifiable;
import model.chatcomponents.user.User;

public class MockUser extends User {
    private int numberOfUpdates;
    public MockUser(String name, String password) {
        super(name, password);

    }

    @Override
    public void updateClients(IIdentifiable iIdentifiable){
        //super.updateClients(message);
        numberOfUpdates++;
    }

    public int getNumberOfReceivedMessages(){
        return numberOfUpdates;
    }
}
