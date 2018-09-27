package model;

import java.util.ArrayList;
import java.util.List;

public class MockClient extends Client{
    private List<IMessage> messages;

    public MockClient(){
        messages = new ArrayList<>();
    }

    @Override
    public void updateListeners(IMessage message) {
        //super.update(message);
        messages.add(message);
    }

    public int getAmountOfMessages(){
        return messages.size();
    }
}
