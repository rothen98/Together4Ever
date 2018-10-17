package controllers;

import model.chatcomponents.channel.IChannel;

public interface IWackController {
    void leftChannel(IChannel channel);

    void openChannelView(IChannel channel);

    void joinChannel(int id);
}
