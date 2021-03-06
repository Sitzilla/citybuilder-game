package com.evansitzes.game.state;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by evan on 12/27/16.
 */
public class Tile {
    @JsonProperty
    private int x;

    @JsonProperty
    private int y;

    @JsonProperty
    private boolean occupied;

//    @JsonProperty
//    private Building building;

    public Tile() {

    }

    public Tile(final int x, final int y) {
        this.x = x;
        this.y = y;
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

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(final boolean occupied) {
        this.occupied = occupied;
    }

//    public Building getBuilding() {
//        return building;
//    }
//
//    public void setBuilding(final Building building) {
//        this.building = building;
//    }
}
