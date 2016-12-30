package com.evansitzes.game.people;

import com.evansitzes.game.CityBuildingGame;
import com.evansitzes.game.environment.TilesMap;
import com.evansitzes.game.people.Person.Facing;
import com.evansitzes.game.people.Person.State;

import java.util.ArrayList;

import static com.evansitzes.game.people.Person.Facing.*;
import static com.evansitzes.game.people.SpriteHelper.*;

/**
 * Created by evan on 12/29/16.
 */
public class SpriteHandler {
    private CityBuildingGame game;
    private ArrayList<Person> persons;
    private static int TILE_SIZE;
    private TilesMap tilesMap;
//    private ArrayList<Building> buildings;

    public SpriteHandler(final CityBuildingGame game, final int TILE_SIZE, final TilesMap tilesMap) {
        this.game = game;
        this.TILE_SIZE = TILE_SIZE;
        this.tilesMap = tilesMap;
        persons = new ArrayList<Person>();

    }

    public void addSpriteToList(final Person person) {
        persons.add(person);
    }

    public void handleAllSprites(final float delta) {
        for (final Person person : persons) {
            handleSprite(person, delta);
        }
    }

    public void handleSprite(final Person person, final float delta) {

        // Normal people can only walk on roads
        if (person.currentBuilding == null || !person.currentBuilding.name.equals("road")) {
            person.state = State.IDLE;
            person.handle(delta);
            return;
        }

        // In the 'leave-square danger zone'
        //TODO magic number
        if ((person.direction == UP && person.edge > person.currentTileY + TILE_SIZE - 5)
                || (person.direction == DOWN && person.edge < person.currentTileY + 5)
                || (person.direction == RIGHT && person.edge > person.currentTileX + TILE_SIZE - 5)
                || (person.direction == LEFT && person.edge < person.currentTileX + 5)) {

            // No available adjacent road
            if (person.nextDirection == null) {
                person.state = State.IDLE;
                person.handle(delta);
                return;
            }

            // Keep walking straight
            if (person.nextDirection == person.direction) {
                final int newTileX = getXCornerTileFromMiddleArea(person.x, TILE_SIZE);
                final int newTileY = getYCornerTileFromMiddleArea(person.y, TILE_SIZE);

                // Still in old square so keep walking straight
                if (newTileX == person.currentTileX && newTileY == person.currentTileY) {
                    person.state = State.WALKING;
                    person.handle(delta);
                    return;
                }

                // Entering new square. Keep walking straight but assign new random direction
                person.currentTileX = getXCornerTileFromMiddleArea(person.x, TILE_SIZE);
                person.currentTileY = getYCornerTileFromMiddleArea(person.y, TILE_SIZE);
                person.nextDirection = getRandomValidDirection(person.x, person.y, reverseDirection(person.direction), TILE_SIZE, tilesMap);
                person.state = State.WALKING;
                person.handle(delta);
                return;

            }

            // Start walking new direction in the same square



            // Start walking a new direction
            person.direction = person.nextDirection;
            //TODO wtf is this?
            person.nextDirection = person.nextDirection;
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

    public void setTilesMap(final TilesMap tilesMap) {
        this.tilesMap = tilesMap;
    }
}
