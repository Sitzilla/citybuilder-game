package com.evansitzes.game.environment;

import java.util.ArrayList;

/**
 * Created by evan on 12/27/16.
 */
public class TilesMap {

    private static ArrayList<ArrayList<Tile>> X_TILES = new ArrayList<ArrayList<Tile>>();

    // Create new Tile Map from flattened list of tiles
    public TilesMap(final ArrayList<Tile> flattenedTiles, final float mapDimensionsX, final float mapDimensionsY, final int tileHeight, final int tileWidth) {
        int counter = 0;

        for (int i = 0; i < mapDimensionsX / tileWidth; i++) {
            final ArrayList<Tile> yTiles = new ArrayList<Tile>();

            for (int j = 0; j < mapDimensionsY / tileHeight; j++) {
                yTiles.add(flattenedTiles.get(counter));
                counter++;
            }

            X_TILES.add(yTiles);
        }
    }


    // Create empty Tile Map from map dimensions
    public TilesMap(final float mapDimensionsX, final float mapDimensionsY, final int tileHeight, final int tileWidth) {
        for (int i = 0; i < mapDimensionsX / tileWidth; i++) {
            final ArrayList<Tile> yTiles = new ArrayList<Tile>();


            for (int j = 0; j < mapDimensionsY / tileHeight; j++) {
                yTiles.add(new Tile(i, j));
            }

            X_TILES.add(yTiles);

        }
    }



    public static Tile getTile(final int x, final int y) {
        return X_TILES.get(x).get(y);
    }

    public static ArrayList<Tile> getFlattenedTiles() {
        final ArrayList<Tile> flattenedTiles = new ArrayList<Tile>();

        for (int i = 0; i < X_TILES.size(); i++) {

            for (int j = 0; j < X_TILES.get(i).size(); j++) {
                final Tile tile = new Tile(i, j);
                tile.setOccupied(getTile(i, j).isOccupied());
                flattenedTiles.add(tile);
            }

        }

        return flattenedTiles;
    }
}
