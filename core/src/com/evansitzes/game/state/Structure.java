package com.evansitzes.game.state;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by evan on 12/27/16.
 */
public class Structure {

    @JsonProperty("sprite_name")
    private String spriteName;

    @JsonProperty("pretty_name")
    private String prettyName;

    @JsonProperty("tile_size")
    private int tileSize;

    @JsonProperty
    private int x;

    @JsonProperty
    private int y;

    public Structure() {
    }

    public String getSpriteName() {
        return spriteName;
    }

    public void setSpriteName(final String spriteName) {
        this.spriteName = spriteName;
    }

    public String getPrettyName() {
        return prettyName;
    }

    public void setPrettyName(final String prettyName) {
        this.prettyName = prettyName;
    }

    public int getTileSize() {
        return tileSize;
    }

    public void setTileSize(final int tileSize) {
        this.tileSize = tileSize;
    }

    public int getX() {
        return x;
    }

    public void setX(final int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(final int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Structure{" +
                "spriteName='" + spriteName + '\'' +
                ", prettyName='" + prettyName + '\'' +
                ", tileSize=" + tileSize +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}
