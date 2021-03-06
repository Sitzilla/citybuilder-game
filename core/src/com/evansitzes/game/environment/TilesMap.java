package com.evansitzes.game.environment;

import com.evansitzes.game.buildings.Building;
import com.evansitzes.game.state.Tile;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by evan on 12/27/16.
 */
public class TilesMap {

    //TODO  why in the world is this not just a 2D array??
    private List<ArrayList<EnhancedTile>> xTiles = new ArrayList<ArrayList<EnhancedTile>>();

    // Create new Tile Map from flattened list of tiles
    public TilesMap(final List<EnhancedTile> flattenedTiles, final float mapDimensionsX, final float mapDimensionsY, final int tileHeight, final int tileWidth) {
        int counter = 0;

        for (int i = 0; i < mapDimensionsX / tileWidth; i++) {
            final ArrayList<EnhancedTile> yTiles = new ArrayList<EnhancedTile>();

            for (int j = 0; j < mapDimensionsY / tileHeight; j++) {
                yTiles.add(flattenedTiles.get(counter));
                counter++;
            }

            xTiles.add(yTiles);
        }
    }


    // Create empty Tile Map from map dimensions
    public TilesMap(final float mapDimensionsX, final float mapDimensionsY, final int tileHeight, final int tileWidth) {
        for (int i = 0; i < mapDimensionsX / tileWidth; i++) {
            final ArrayList<EnhancedTile> yTiles = new ArrayList<EnhancedTile>();


            for (int j = 0; j < mapDimensionsY / tileHeight; j++) {
                yTiles.add(new EnhancedTile(i, j));
            }

            xTiles.add(yTiles);

        }
    }



    public EnhancedTile getTile(final int x, final int y) {
        try {
            return xTiles.get(x).get(y);
        } catch (final IndexOutOfBoundsException e) {
            return null;
        }
    }

    public List<Tile> getFlattenedTiles() {
        final List<Tile> flattenedTiles = new ArrayList<Tile>();

        for (int i = 0; i < xTiles.size(); i++) {

            for (int j = 0; j < xTiles.get(i).size(); j++) {
                final Tile tile = new Tile(i, j);
                tile.setOccupied(getTile(i, j).isOccupied());
                flattenedTiles.add(tile);
            }

        }

        return flattenedTiles;
    }

    //TODO measure how long this takes
    public void addAllBuildings(final List<Building> buildings) {

        if (buildings.size() == 0) {
            return;
        }

        for (int i = 0; i < xTiles.size(); i++) {

            for (int j = 0; j < xTiles.get(i).size(); j++) {

                for (final Building building : buildings) {
                    //TODO magic numbers
                    if (building.overhangs(xTiles.get(i).get(j).getX() * 32, xTiles.get(i).get(j).getY() * 32)) {
                        xTiles.get(i).get(j).setBuilding(building);
                    }

                }

            }

        }


    }

    public void resetAllTileChecks() {
        for (int i = 0; i < xTiles.size(); i++) {
            for (int j = 0; j < xTiles.get(i).size(); j++) {
                xTiles.get(i).get(j).setHasBeenChecked(false);
            }
        }
    }
}
