package model;

import javafx.scene.image.Image;

/**
 * This class contains information about the channel's profile.
 * The channel's name, description, image and id.
 */
public class ChannelProfile implements IInformative {
    private String name;
    private Image image;
    private int id;
    private String description;

    public ChannelProfile(String name, Image image, int id, String description) {
        this.name = name;
        this.image = image;
        this.id = id;
        this.description = description;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public int getID() {
        return id;
    }

    @Override
    public String getDisplayName() {
        return name;
    }

    @Override
    public Image getDisplayImage() {
        return image;
        //TODO this should return a copy?
    }
}
