package util;

import model.chatcomponents.user.IUser;

public class UserContainer {
    private IUser user;
    private String password;

    public UserContainer(IUser user, String password) {
        this.user = user;
        this.password = password;
    }

    public IUser getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }
}
