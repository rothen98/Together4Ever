package model;

import java.util.ArrayList;
import java.util.List;

public class MockClient extends Client{
    private int numberOFMessages;

    public MockClient(){

    }

    @Override
    public void updateListeners(IIdentifiable message) {
        //super.update(message);
        numberOFMessages++;
    }

    public int getAmountOfMessages(){
        return numberOFMessages;
    }
}
