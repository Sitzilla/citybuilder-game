package com.evansitzes.game.people;

import com.evansitzes.game.buildings.Building;
import com.evansitzes.game.environment.TilesMap;

import java.util.ArrayList;
import java.util.Random;

import static com.evansitzes.game.people.Person.Facing.DOWN;

/**
 * Created by evan on 12/30/16.
 */
public class SpriteHelper {

    public static Person.Facing getRandomValidDirection(final int xPixels, final int yPixels, final int buildingSize, final Person.Facing directionSpriteCameFrom, final int TILE_SIZE, final TilesMap tilesMap) {
        final ArrayList<Person.Facing> directions = getAllAvailableRoads(xPixels, yPixels, buildingSize, directionSpriteCameFrom, TILE_SIZE, tilesMap);

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

    public static int getNextXCornerTileFromDirection(final int x, final int TILE_SIZE, final int buildingSize, final Person.Facing direction) {
        switch (direction) {
            case DOWN: return x;
            case UP: return x;
            case RIGHT: return x + (TILE_SIZE * buildingSize);
            case LEFT: return x - TILE_SIZE;
        }

        //TODO should throw an error here
        return 0;
    }

    public static int getNextYCornerTileFromDirection(final int y, final int TILE_SIZE, final int buildingSize, final Person.Facing direction) {
        switch (direction) {
            case DOWN: return y - TILE_SIZE;
            case UP: return y + (TILE_SIZE * buildingSize);
            case RIGHT: return y;
            case LEFT: return y;
        }

        //TODO should throw an error here
        return 0;
    }

    private static ArrayList<Person.Facing> getAllAvailableRoads(final int xPixels, final int yPixels, final int buildingSize, final Person.Facing directionSpriteCameFrom, final int TILE_SIZE, final TilesMap tilesMap) {
        final ArrayList<Person.Facing> facing = new ArrayList<Person.Facing>();

        // Check DOWN square
        if (directionSpriteCameFrom != DOWN && isNextTileIsRoad(xPixels, yPixels - TILE_SIZE, TILE_SIZE, tilesMap)) {
            facing.add(DOWN);
        }

        // Check UP square
        if (directionSpriteCameFrom != Person.Facing.UP && isNextTileIsRoad(xPixels, yPixels + (TILE_SIZE * buildingSize), TILE_SIZE, tilesMap)) {
            facing.add(Person.Facing.UP);
        }

        // Check LEFT square
        if (directionSpriteCameFrom != Person.Facing.LEFT && isNextTileIsRoad(xPixels - TILE_SIZE, yPixels, TILE_SIZE, tilesMap)) {
            facing.add(Person.Facing.LEFT);
        }

        // Check RIGHT square
        if (directionSpriteCameFrom != Person.Facing.RIGHT && isNextTileIsRoad(xPixels + (TILE_SIZE * buildingSize), yPixels, TILE_SIZE, tilesMap)) {
            facing.add(Person.Facing.RIGHT);
        }

        return facing;
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
