package model;

import java.util.ArrayList;
import java.util.Collection;

public class MockListener implements IClientListener {
    private Collection messages = new ArrayList();

    @Override
    public void update(IMessage message) {
        messages.add(message);
    }

    public int getAmountOfMessages(){
        return messages.size();
    }
}
