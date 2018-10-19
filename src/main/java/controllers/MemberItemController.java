package controllers;

import views.IMemberItemController;

public class MemberItemController implements IMemberItemController {

    private IMemberItemParent parent;

    public MemberItemController(IMemberItemParent parent) {
        this.parent = parent;
    }

    @Override
    public void kickUser(String username) {
        parent.kickUser(username);
    }
}
