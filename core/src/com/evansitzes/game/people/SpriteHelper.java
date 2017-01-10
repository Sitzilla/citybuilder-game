package com.evansitzes.game.people;

import com.evansitzes.game.buildings.Building;
import com.evansitzes.game.environment.EnhancedTile;
import com.evansitzes.game.environment.TilesMap;
import com.evansitzes.game.helpers.Direction;
import com.evansitzes.game.people.sprites.Person.Facing;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.evansitzes.game.people.sprites.Person.Facing.*;

/**
 * Created by evan on 12/30/16.
 */
public class SpriteHelper {

    public static Direction getRandomValidDirection(final int xPixels, final int yPixels, final int buildingSize, final Facing directionSpriteCameFrom, final int TILE_SIZE, final TilesMap tilesMap) {
        final ArrayList<Direction> directions = getAllAvailableRoads(xPixels, yPixels, buildingSize, directionSpriteCameFrom, TILE_SIZE, tilesMap);

//        System.out.println("Available directions: ");
//        for (Person.Facing facing : directions) {
////            System.out.print(facing + "  ");
//        }
//        System.out.println("================");

        if (directions.isEmpty()) {
            return null;
        }

        final Random rand = new Random();
        return directions.get(rand.nextInt(directions.size()));
    }

    public static int getXCornerTileFromMiddleArea(final int screenX, final int TILE_SIZE) {
        return screenX / TILE_SIZE * TILE_SIZE;
    }

    public static int getYCornerTileFromMiddleArea(final int screenY, final int TILE_SIZE) {
        return screenY / TILE_SIZE * TILE_SIZE;
    }

    public static int getNextXCornerTileFromDirection(final int x, final int TILE_SIZE, final int buildingSize, int directionIndex, final Facing direction) {
        switch (direction) {
            case DOWN: return x + (TILE_SIZE * directionIndex);
            case UP: return x + (TILE_SIZE * directionIndex);
            case RIGHT: return x + (TILE_SIZE * buildingSize);
            case LEFT: return x - TILE_SIZE;
        }

        //TODO should throw an error here
        return 0;
    }

    public static int getNextYCornerTileFromDirection(final int y, final int TILE_SIZE, final int buildingSize, int directionIndex, final Facing direction) {
        switch (direction) {
            case DOWN: return y - TILE_SIZE;
            case UP: return y + (TILE_SIZE * buildingSize);
            case RIGHT: return y + (TILE_SIZE * directionIndex);
            case LEFT: return y + (TILE_SIZE * directionIndex);
        }

        //TODO should throw an error here
        return 0;
    }

    public static List<EnhancedTile> getBuildingsAdjacentRoads(final int xPixels, final int yPixels, final int buildingSize, final int TILE_SIZE, final TilesMap tilesMap) {
        final List<Direction> adjacentRoadDirections = getAllAvailableRoads(xPixels, yPixels, buildingSize, null, TILE_SIZE, tilesMap);
        final List<EnhancedTile> adjacentRoadTiles = new ArrayList<EnhancedTile>();

        for (final Direction direction : adjacentRoadDirections) {
            switch (direction.facingDirection) {
                case DOWN: adjacentRoadTiles.add(tilesMap.getTile((xPixels + (direction.directionIndex * TILE_SIZE)) / TILE_SIZE, (yPixels - TILE_SIZE) / TILE_SIZE));
                            break;

                case UP: adjacentRoadTiles.add(tilesMap.getTile((xPixels + (direction.directionIndex * TILE_SIZE)) / TILE_SIZE, (yPixels + (TILE_SIZE * buildingSize)) / TILE_SIZE));
                            break;

                case LEFT: adjacentRoadTiles.add(tilesMap.getTile((xPixels - TILE_SIZE) / TILE_SIZE, (yPixels + (direction.directionIndex * TILE_SIZE)) / TILE_SIZE));
                            break;

                case RIGHT: adjacentRoadTiles.add(tilesMap.getTile((xPixels + (TILE_SIZE * buildingSize)) / TILE_SIZE, (yPixels + (direction.directionIndex * TILE_SIZE)) / TILE_SIZE));
                            break;
            }

        }

        return adjacentRoadTiles;
    }

    private static ArrayList<Direction> getAllAvailableRoads(final int xPixels, final int yPixels, final int buildingSize, final Facing directionSpriteCameFrom, final int TILE_SIZE, final TilesMap tilesMap) {
        final ArrayList<Direction> directions = new ArrayList<Direction>();

        // Check DOWN squares
        if (directionSpriteCameFrom != DOWN) {
            for (int i = 0; i < buildingSize; i++) {
                if (isNextTileIsRoad(xPixels + (TILE_SIZE * i), yPixels - TILE_SIZE, TILE_SIZE, tilesMap)) {
                    directions.add(new Direction(i, DOWN));
                }
            }
        }

        // Check UP squares
        if (directionSpriteCameFrom != UP) {
            for (int i = 0; i < buildingSize; i++) {
                if (isNextTileIsRoad(xPixels + (TILE_SIZE * i), yPixels + (TILE_SIZE * buildingSize), TILE_SIZE, tilesMap)) {
                    directions.add(new Direction(i, UP));
                }
            }
        }

        // Check LEFT squares
        if (directionSpriteCameFrom != LEFT) {
            for (int i = 0; i < buildingSize; i++) {
                if (isNextTileIsRoad(xPixels - TILE_SIZE, yPixels + (TILE_SIZE * i), TILE_SIZE, tilesMap)) {
                    directions.add(new Direction(i, LEFT));
                }
            }
        }

        // Check RIGHT squares
        if (directionSpriteCameFrom != RIGHT) {
            for (int i = 0; i < buildingSize; i++) {
                if (isNextTileIsRoad(xPixels + (TILE_SIZE * buildingSize), yPixels + (TILE_SIZE * i), TILE_SIZE, tilesMap)) {
                    directions.add(new Direction(i, RIGHT));
                }
            }
        }

        return directions;
    }

    private static boolean isNextTileIsRoad(final int xPixels, final int yPixels, final int TILE_SIZE, final TilesMap tilesMap) {

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
