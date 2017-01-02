package com.evansitzes.game.state;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by evan on 12/27/16.
 */
public class Structure {

    @JsonProperty("sprite_name")
    private String spriteName;

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

    public void setSpriteName(String spriteName) {
        this.spriteName = spriteName;
    }

    public int getTileSize() {
        return tileSize;
    }

    public void setTileSize(int tileSize) {
        this.tileSize = tileSize;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Structure{" +
                "spriteName='" + spriteName + '\'' +
                ", tileSize=" + tileSize +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}
