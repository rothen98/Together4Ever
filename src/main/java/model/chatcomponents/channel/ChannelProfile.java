package model.chatcomponents.channel;

import model.identifiers.IIdentifiable;

/**
 * This class contains information about the channel's profile.
 * The channel's name, description, image and id.
 */
public class ChannelProfile implements IIdentifiable {
    private String name;
    private String image;
    private int id;
    private String description;

    public ChannelProfile(String name, int id, String description) {
        this.name = name;
        //Set this.image to deafult channel image
        this.id = id;
        this.description = description;
    }

    /**
     *
     * @return The description of the channel
     */
    @Override
    public String getDescription() {
        return description;
    }

    /**
     *
     * @return The channel's id
     */
    @Override
    public int getID() {
        return id;
    }

    /**
     *
     * @return The name of the channel
     */
    @Override
    public String getDisplayName() {
        return name;
    }

    /**
     * The image of the channel
     * @return
     */
    @Override
    public String getDisplayImage() {
        return image;
        //TODO this should return a copy?
    }
}
