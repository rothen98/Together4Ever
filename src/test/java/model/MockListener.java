package model;

import model.client.IClientListener;
import model.identifiers.IIdentifiable;

public class MockListener implements IClientListener {
    private int numberOfUpdates;

    @Override
    public void update(IIdentifiable iIdentifiable) {
        numberOfUpdates++;
    }

    public int getAmountOfMessages(){
        return numberOfUpdates;
    }
}
