package model;

import java.util.ArrayList;
import java.util.List;

public class MockUser extends User {
    private List<IMessage> messages;
    public MockUser(String name, String password) {
        super(name, password);
        messages = new ArrayList<>();
    }

    @Override
    public void sendMessageToClients(IIdentifiable iIdentifiable){
        //super.sendMessageToClients(message);
        messages.add(message);
    }

    public List<IMessage> getReceivedMessages(){
        return messages;
    }
}
