package model;

import java.util.Collection;
import java.util.List;

public class Channel implements IChannel {
    @Override
    public Collection<String> getAllUserNames() {
        return null;
    }

    @Override
    public List<IMessage> getAllMessages() {
        return null;
    }

    @Override
    public List<IMessage> getLastMessages(int index) {
        return null;
    }

    @Override
    public void join(IUser user) {

    }

    @Override
    public void leave(IUser user) {

    }

    @Override
    public String getName() {
        return null;
    }
}
