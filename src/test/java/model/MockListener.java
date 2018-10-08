package model;

import java.util.ArrayList;
import java.util.Collection;

public class MockListener implements IClientListener {
    private Collection messages = new ArrayList();

    @Override
    public void update(IIdentifiable iIdentifiable) {
        messages.add(message);
    }

    public int getAmountOfMessages(){
        return messages.size();
    }
}
