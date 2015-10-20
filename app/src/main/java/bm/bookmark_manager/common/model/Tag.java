package bm.bookmark_manager.common.model;

import android.graphics.Color;

import bm.bookmark_manager.common.Constants;

public class Tag extends Model {

    private String id;
    private String name;
    private String color;
    private String owner;

    /**
     * Return the tag color-int value to use on view
     * @return int the color value
     */
    public int getColorValue() {
        try {
            return Color.parseColor(this.color);
        }
        catch (IllegalArgumentException e) { // the color is invalid
            return Color.parseColor(Constants.tag.DEFAULT_COLOR);
        }
    }

    @Override
    protected String getClassName() {
        return "Tag";
    }

    // -- Getters and setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public boolean canBeFilter(String searchQuery) {
        return name.toUpperCase().contains(searchQuery.toUpperCase());
    }
}
