package model;

import java.util.ArrayList;
import java.util.Collection;

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
