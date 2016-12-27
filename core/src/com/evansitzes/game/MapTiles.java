package com.evansitzes.game;

import java.util.ArrayList;

/**
 * Created by evan on 12/27/16.
 */
public class MapTiles {

    private static float mapDimensionsX;
    private static float mapDimensionsY;

    private static ArrayList<ArrayList<Tile>> X_TILES = new ArrayList<ArrayList<Tile>>();

    public MapTiles(final float mapDimensionsX, final float mapDimensionsY, final int tileHeight, final int tileWidth) {
        this.mapDimensionsX = mapDimensionsX;
        this.mapDimensionsY = mapDimensionsY;

        for (int i = 0; i < mapDimensionsX / (tileHeight * Configuration.HEIGHT_MODIFIER); i++) {
            final ArrayList<Tile> yTiles = new ArrayList<Tile>();


            for (int j = 0; j < mapDimensionsY / (tileWidth * Configuration.WIDTH_MODIFIER); j++) {
                yTiles.add(new Tile(i, j));
            }

            X_TILES.add(yTiles);

        }
    }

    public static Tile getTile(final int x, final int y) {
        return X_TILES.get(x).get(y);
    }
}
