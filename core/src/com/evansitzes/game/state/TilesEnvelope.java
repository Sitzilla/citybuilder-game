package com.evansitzes.game.state;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

/**
 * Created by evan on 12/27/16.
 */
public class TilesEnvelope {
    @JsonProperty
    private ArrayList<Tile> tiles;

    public TilesEnvelope() {
    }

    public ArrayList<Tile> getTiles() {
        return tiles;
    }

    public void setTiles(ArrayList<Tile> tiles) {
        this.tiles = tiles;
    }

    @Override
    public String toString() {
        return "TilesEnvelope{" +
                "tiles=" + tiles +
                '}';
    }
}
