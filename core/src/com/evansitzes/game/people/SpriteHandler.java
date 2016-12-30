package com.evansitzes.game.people;

import com.evansitzes.game.CityBuildingGame;
import com.evansitzes.game.buildings.Building;
import com.evansitzes.game.environment.TilesMap;
import com.evansitzes.game.people.Person.Facing;
import com.evansitzes.game.people.Person.State;

import java.util.ArrayList;
import java.util.Random;

import static com.evansitzes.game.people.Person.Facing.*;

/**
 * Created by evan on 12/29/16.
 */
public class SpriteHandler {
    private CityBuildingGame game;
    private Person person;
    private static int TILE_SIZE;
    private TilesMap tilesMap;
//    private ArrayList<Building> buildings;

    private Building currentBuilding;
    private Facing nextDirection;

    private int currentTileX;
    private int currentTileY;

    public SpriteHandler(final CityBuildingGame game, final Person person, final int TILE_SIZE, final TilesMap tilesMap) {
        this.game = game;
        this.person = person;
        this.TILE_SIZE = TILE_SIZE;
        this.tilesMap = tilesMap;

        // initialize of sprite phase
        person.state = State.IDLE;
        person.direction = DOWN;
        currentTileX = getXCornerTileFromMiddleArea((int) person.x);
        currentTileY = getYCornerTileFromMiddleArea((int) person.y);
        currentBuilding = tilesMap.getTile(currentTileX / TILE_SIZE, currentTileY / TILE_SIZE).getBuilding();
        // TODO hardcoded to down
        //TODO remove Rectangle extension so we dont have to cast to ints everywhere
        nextDirection = getRandomValidDirection((int) person.x, (int) person.y, UP);
    }

    public void handleSprite(final float delta) {

        // Normal people can only walk on roads
        if (currentBuilding == null || !currentBuilding.name.equals("road")) {
            person.state = State.IDLE;
            person.handle(delta);
            return;
        }

        // In the 'leave-square danger zone'
        //TODO magic number
        if ((person.direction == UP && person.edge > currentTileY + TILE_SIZE - 5)
                || (person.direction == DOWN && person.edge < currentTileY + 5)
                || (person.direction == RIGHT && person.edge > currentTileX + TILE_SIZE - 5)
                || (person.direction == LEFT && person.edge < currentTileX + 5)) {

            // No available adjacent road
            if (nextDirection == null) {
                person.state = State.IDLE;
                person.handle(delta);
                return;
            }

            // Keep walking straight
            if (nextDirection == person.direction) {
                final int newTileX = getXCornerTileFromMiddleArea((int) person.x);
                final int newTileY = getYCornerTileFromMiddleArea((int) person.y);

                // Still in old square so keep walking straight
                if (newTileX == currentTileX && newTileY == currentTileY) {
                    person.state = State.WALKING;
                    person.handle(delta);
                    return;
                }

                // Entering new square. Keep walking straight but assign new random direction
                currentTileX = getXCornerTileFromMiddleArea((int) person.x);
                currentTileY = getYCornerTileFromMiddleArea((int) person.y);
                nextDirection = getRandomValidDirection((int) person.x, (int) person.y, reverseDirection(person.direction));
                person.state = State.WALKING;
                person.handle(delta);
                return;

            }

            // Start walking new direction in the same square



            // Start walking a new direction
            person.direction = nextDirection;
            nextDirection = nextDirection;
//            nextDirection = getRandomValidDirection((int) person.x, (int) person.y, reverseDirection(currentDirection));
            person.state = State.WALKING;
            person.handle(delta);
            return;
        }

        // Default: keep walking straight
        person.state = State.WALKING;
        person.handle(delta);
    }

    private Facing reverseDirection(final Facing currentDirection) {

        switch (currentDirection) {
            case DOWN: return Facing.UP;
            case UP: return Facing.DOWN;
            case RIGHT: return Facing.LEFT;
            case LEFT: return Facing.RIGHT;
        }
        //TODO should throw an error here

        return null;
    }

    private Facing getRandomValidDirection(final int xPixels, final int yPixels, final Facing directionSpriteCameFrom) {
        final ArrayList<Facing> directions = getAllAvailableRoads(xPixels, yPixels, directionSpriteCameFrom);

        System.out.println("Available directions: ");
        for (Facing facing : directions) {
            System.out.print(facing + "  ");
        }
        System.out.println("================");

        if (directions.isEmpty()) {
            return null;
        }

        final Random rand = new Random();
        return directions.get(rand.nextInt(directions.size()));
    }

    private ArrayList<Facing> getAllAvailableRoads(final int xPixels, final int yPixels, final Facing directionSpriteCameFrom) {
        final ArrayList<Facing> facing = new ArrayList<Facing>();

        // Check DOWN square
        if (directionSpriteCameFrom != DOWN && isNextTileIsRoad(xPixels, yPixels - TILE_SIZE)) {
            facing.add(DOWN);
        }

        // Check UP square
        if (directionSpriteCameFrom != Facing.UP && isNextTileIsRoad(xPixels, yPixels + TILE_SIZE)) {
            facing.add(Facing.UP);
        }

        // Check LEFT square
        if (directionSpriteCameFrom != Facing.LEFT && isNextTileIsRoad(xPixels - TILE_SIZE, yPixels)) {
            facing.add(Facing.LEFT);
        }

        // Check RIGHT square
        if (directionSpriteCameFrom != Facing.RIGHT && isNextTileIsRoad(xPixels + TILE_SIZE, yPixels)) {
            facing.add(Facing.RIGHT);
        }

        return facing;
    }

    private boolean isNextTileIsRoad(final int xPixels, final int yPixels) {
        final Building nextBuilding = tilesMap.getTile(xPixels / TILE_SIZE, yPixels / TILE_SIZE).getBuilding();
        if (nextBuilding == null) {
            return false;
        }
        return tilesMap.getTile(xPixels / TILE_SIZE, yPixels / TILE_SIZE).getBuilding().name.equals("road");
    }

    private int getXCornerTileFromMiddleArea(final int screenX) {
            return screenX / TILE_SIZE * TILE_SIZE;
    }

    private int getYCornerTileFromMiddleArea(final int screenY) {
        return screenY / TILE_SIZE * TILE_SIZE;
    }

    public void setTilesMap(final TilesMap tilesMap) {
        this.tilesMap = tilesMap;
    }
}
