package com.evansitzes.game.environment;

import com.evansitzes.game.buildings.Building;
import com.evansitzes.game.state.Tile;

import java.util.ArrayList;

/**
 * Created by evan on 12/27/16.
 */
public class TilesMap {

    private ArrayList<ArrayList<EnhancedTile>> xTiles = new ArrayList<ArrayList<EnhancedTile>>();

    // Create new Tile Map from flattened list of tiles
    public TilesMap(final ArrayList<EnhancedTile> flattenedTiles, final float mapDimensionsX, final float mapDimensionsY, final int tileHeight, final int tileWidth) {
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
        return xTiles.get(x).get(y);
    }

    public ArrayList<Tile> getFlattenedTiles() {
        final ArrayList<Tile> flattenedTiles = new ArrayList<Tile>();

        for (int i = 0; i < xTiles.size(); i++) {

            for (int j = 0; j < xTiles.get(i).size(); j++) {
                final Tile tile = new Tile(i, j);
                System.out.println("Adding!");
                tile.setOccupied(getTile(i, j).isOccupied());
                flattenedTiles.add(tile);
            }

        }

        return flattenedTiles;
    }

    //TODO measure how long this takes
    public void addAllBuildings(final ArrayList<Building> buildings) {

        if (buildings.size() == 0) {
            return;
        }

        for (int i = 0; i < xTiles.size(); i++) {

            for (int j = 0; j < xTiles.get(i).size(); j++) {

                for (final Building building : buildings) {
                    //TODO magic numbers
                    if (building.overhangs(xTiles.get(i).get(j).getX() * 32, xTiles.get(i).get(j).getY() * 32)) {
                        System.out.println("Adding!");
                        xTiles.get(i).get(j).setBuilding(building);
                    }

                }

            }

        }


    }
}
