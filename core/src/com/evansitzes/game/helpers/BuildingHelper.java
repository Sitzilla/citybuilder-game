package com.evansitzes.game.helpers;

import com.evansitzes.game.CityBuildingGame;
import com.evansitzes.game.buildings.Building;
import com.evansitzes.game.buildings.Road;
import com.evansitzes.game.environment.EnhancedTile;
import com.evansitzes.game.environment.TilesMap;
import com.evansitzes.game.state.Definition;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by evan on 1/21/17.
 */
public class BuildingHelper {

    private static Map<String, Definition> OBJECT_DEFINITIONS;
    private static int TILE_SIZE;

    public BuildingHelper(final Map<String, Definition> objectDefinitions, final int TILE_SIZE) {
        this.OBJECT_DEFINITIONS = objectDefinitions;
        this.TILE_SIZE = TILE_SIZE;
    }

    public static Building createGenericBuilding(final CityBuildingGame game, final String name) {
        final Building building = new Building(game, OBJECT_DEFINITIONS.get(name).getTileSize(), OBJECT_DEFINITIONS.get(name).getType());
        building.name = name;
        building.prettyName = OBJECT_DEFINITIONS.get(name).getPrettyName();
        building.description = OBJECT_DEFINITIONS.get(name).getDescription();

        return building;
    }

    public static Building createBlankBuilding(final CityBuildingGame game) {
        final Building building = new Building();
        building.tileSize = 1;
        return building;
    }

    public static boolean isBuildingConnectedToRoad(final Building building, final TilesMap tilesMap) {

        // Check DOWN squares
        for (int i = 0; i < building.tileSize; i++) {
            if (isNextTileIsRoad(building.x + (TILE_SIZE * i), building.y - TILE_SIZE, tilesMap)) {
                return true;
            }
        }

        // Check UP squares
        for (int i = 0; i < building.tileSize; i++) {
            if (isNextTileIsRoad(building.x + (TILE_SIZE * i), building.y + (TILE_SIZE * building.tileSize), tilesMap)) {
                return true;
            }
        }

        // Check LEFT squares
        for (int i = 0; i < building.tileSize; i++) {
            if (isNextTileIsRoad(building.x - TILE_SIZE, building.y + (TILE_SIZE * i), tilesMap)) {
                return true;
            }
        }

        // Check RIGHT squares
        for (int i = 0; i < building.tileSize; i++) {
            if (isNextTileIsRoad(building.x + (TILE_SIZE * building.tileSize), building.y + (TILE_SIZE * i), tilesMap)) {
                return true;
            }
        }

        return false;
    }

    public static List<Building> getAdjacentBuildings(final Road road, final TilesMap tilesMap) {
        final List<Building> buildings = new ArrayList<Building>();
        EnhancedTile enhancedTile;

        // Check DOWN square
        enhancedTile = getSquare(road.x, road.y - TILE_SIZE, tilesMap);
        if (enhancedTile.isOccupied() && enhancedTile.getBuilding().name != "road") {
            buildings.add(enhancedTile.getBuilding());
        }

        // Check UP square
        enhancedTile = getSquare(road.x, road.y + TILE_SIZE, tilesMap);
        if (enhancedTile.isOccupied() && enhancedTile.getBuilding().name != "road") {
            buildings.add(enhancedTile.getBuilding());
        }

        // Check LEFT square
        enhancedTile = getSquare(road.x - TILE_SIZE, road.y, tilesMap);
        if (enhancedTile.isOccupied() && enhancedTile.getBuilding().name != "road") {
            buildings.add(enhancedTile.getBuilding());
        }

        // Check RIGHT square
        enhancedTile = getSquare(road.x + TILE_SIZE, road.y, tilesMap);
        if (enhancedTile.isOccupied() && enhancedTile.getBuilding().name != "road") {
            buildings.add(enhancedTile.getBuilding());
        }

        return buildings;
    }

    private static EnhancedTile getSquare(final int x, final int y, final TilesMap tilesMap) {
        return tilesMap.getTile(x / TILE_SIZE, y / TILE_SIZE);
    }

    private static boolean isNextTileIsRoad(final int xPixels, final int yPixels, final TilesMap tilesMap) {

        // Check for edge of the map
        if (xPixels < 0 || yPixels < 0 || xPixels / TILE_SIZE >= TILE_SIZE || yPixels / TILE_SIZE >= TILE_SIZE) {
            return false;
        }

        final Building nextBuilding = tilesMap.getTile(xPixels / TILE_SIZE, yPixels / TILE_SIZE).getBuilding();
        if (nextBuilding == null) {
            return false;
        }
        return tilesMap.getTile(xPixels / TILE_SIZE, yPixels / TILE_SIZE).getBuilding().name.equals("road");
    }

}