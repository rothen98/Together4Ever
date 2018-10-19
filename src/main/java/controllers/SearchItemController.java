package controllers;

import model.identifiers.IIdentifiable;
import views.ISearchItemController;

public class SearchItemController implements ISearchItemController {

    private ISearchItemParent parentcontroller;
    private IIdentifiable channel;

    public SearchItemController(ISearchItemParent parentcontroller, IIdentifiable channel) {
        this.parentcontroller = parentcontroller;
        this.channel = channel;
    }

    @Override
    public void joinChannel() {
        parentcontroller.joinChannel(channel.getID());
    }
}
