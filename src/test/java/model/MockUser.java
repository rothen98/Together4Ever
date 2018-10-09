package model;

import java.util.ArrayList;
import java.util.List;

public class MockUser extends User {
    private int numberOfUpdates;
    public MockUser(String name, String password) {
        super(name, password);

    }

    @Override
    public void sendMessageToClients(IIdentifiable iIdentifiable){
        //super.sendMessageToClients(message);
        numberOfUpdates++;
    }

    public int getNumberOfReceivedMessages(){
        return numberOfUpdates;
    }
}
