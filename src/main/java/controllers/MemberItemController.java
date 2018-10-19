package controllers;

import views.IMemberItem;
import views.IMemberItemController;

public class MemberItemController implements IMemberItemController {

    private IMemberItemParent parent;
    private String username;
    private IMemberItem item;
    public MemberItemController(IMemberItemParent parent, IMemberItem item,String username) {
        this.parent = parent;
        this.username = username;
        this.item = item;
    }

    @Override
    public void kickUser() {
        parent.kickUser(username);
    }

    @Override
    public String getMemberName() {
        return username;
    }

    public void setAdmin(boolean isAdmin) {
        if(isAdmin){
            item.showKickButton();
        }else{
            item.hideKickbutton();
        }
    }
}
