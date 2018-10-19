package controllers;

import views.IMemberItemController;

public class MemberItemController implements IMemberItemController {

    private IMemberItemParent parent;
    private String username;

    public MemberItemController(IMemberItemParent parent, String username) {
        this.parent = parent;
        this.username = username;
    }

    @Override
    public void kickUser() {
        parent.kickUser(username);
    }

    @Override
    public String getMemberName() {
        return username;
    }
}
