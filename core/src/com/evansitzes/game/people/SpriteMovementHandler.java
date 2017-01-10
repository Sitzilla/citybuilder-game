package com.evansitzes.game.people;

import com.evansitzes.game.CityBuildingGame;
import com.evansitzes.game.environment.TilesMap;
import com.evansitzes.game.people.sprites.Person;
import com.evansitzes.game.people.sprites.Person.Facing;
import com.evansitzes.game.people.sprites.Person.State;

import java.util.ArrayList;

import static com.evansitzes.game.people.SpriteHelper.*;
import static com.evansitzes.game.people.sprites.Person.Facing.*;

/**
 * Created by evan on 12/29/16.
 */
public class SpriteMovementHandler {

    private static final int PIXELS_AWAY_FROM_EDGE_OF_TILE = 5;

    private CityBuildingGame game;
    private static int TILE_SIZE;
    private TilesMap tilesMap;

    public SpriteMovementHandler(final CityBuildingGame game, final int TILE_SIZE, final TilesMap tilesMap) {
        this.game = game;
        this.TILE_SIZE = TILE_SIZE;
        this.tilesMap = tilesMap;
    }

    public void handlePatrollingSprites(final float delta, final ArrayList<Person> persons) {
        for (final Person person : persons) {
            handlePatrollingSprite(person, delta);
        }
    }

    public void handleReturningHomeSprites(final float delta, final ArrayList<Person> persons) {
        for (final Person person : persons) {
            handleReturningHomeSprite(person, delta);
        }
    }

    //TODO check for null
    public void handleReturningHomeSprite(final Person person, final float delta) {

        // If no more paths then you are done
        if (person.pathHome.isEmpty()) {
            person.state = State.IDLE;
            person.handle(delta);
            return;
        }

        // Normal people can only walk on roads
        if (person.currentBuilding == null || !person.currentBuilding.name.equals("road")) {
            person.state = State.IDLE;
            person.handle(delta);
            return;
        }

        // If just returning home then finish walking to the end of current tile
        if (person.justBeganReturningHome) {

            if (person.direction == UP && person.pathHome.peek() != UP) {
                person.direction = DOWN;
                person.nextDirection.facingDirection = person.pathHome.pop();
            } else if (person.direction == RIGHT && person.pathHome.peek() != RIGHT) {
                person.direction = LEFT;
                person.nextDirection.facingDirection = person.pathHome.pop();
            } else {
                person.direction = person.pathHome.pop();
                person.nextDirection.facingDirection = person.direction;

            }

            person.justBeganReturningHome = false;
//                try {
//            person.direction = person.pathHome.pop();
//            person.justBeganReturningHome = false;
//                    person.nextDirection.facingDirection = person.pathHome.pop();
//            person.nextDirection.facingDirection = person.direction;
//                    patrolPerson.justBeganReturningHome = true;
//                }catch(EmptyStackException e){
//                    spriteStateHandler.getPatrollingPersons().remove(patrolPerson);
//                }
        }


        // In the 'leave-square danger zone'
        if ((person.direction == UP && person.edge > person.currentTileY + TILE_SIZE - PIXELS_AWAY_FROM_EDGE_OF_TILE)
                || (person.direction == DOWN && person.edge < person.currentTileY + PIXELS_AWAY_FROM_EDGE_OF_TILE)
                || (person.direction == RIGHT && person.edge > person.currentTileX + TILE_SIZE - PIXELS_AWAY_FROM_EDGE_OF_TILE)
                || (person.direction == LEFT && person.edge < person.currentTileX + PIXELS_AWAY_FROM_EDGE_OF_TILE)) {

//            if (person.justBeganReturningHome) {
//
////                try {
//                    person.direction = person.pathHome.pop();
//                    person.justBeganReturningHome = false;
////                    person.nextDirection.facingDirection = person.pathHome.pop();
//                    person.nextDirection.facingDirection = person.direction;
////                    patrolPerson.justBeganReturningHome = true;
////                }catch(EmptyStackException e){
////                    spriteStateHandler.getPatrollingPersons().remove(patrolPerson);
////                }
//            }

//            person.justBeganReturningHome = false;

            // Did the sprite just begin his journey home?
//            if (person.justBeganReturningHome && person.currentTileX / TILE_SIZE)

            // No available adjacent road
            if (person.nextDirection == null) {
                System.out.println("No next direction!");
//                // Check the road behind
//                person.nextDirection = getRandomValidDirection(person.currentTileX, person.currentTileY, 1, null, TILE_SIZE, tilesMap);
//                if (person.nextDirection == null) {
//                    person.state = State.IDLE;
//                    person.handle(delta);
//                    return;
//                }
            }

            // Keep walking straight
            if (person.nextDirection.facingDirection == person.direction) {
                final int newTileX = getXCornerTileFromMiddleArea(person.x, TILE_SIZE);
                final int newTileY = getYCornerTileFromMiddleArea(person.y, TILE_SIZE);

                // Still in old square so keep walking straight
                if (newTileX == person.currentTileX && newTileY == person.currentTileY) {
                    person.state = State.WALKING;
                    person.handle(delta);
                    return;
                }

                // Entering new square. Keep walking straight but assign next direction
                person.currentTileX = getXCornerTileFromMiddleArea(person.x, TILE_SIZE);
                person.currentTileY = getYCornerTileFromMiddleArea(person.y, TILE_SIZE);

                if (person.pathHome.isEmpty()) {
                    person.state = State.IDLE;
                    person.handle(delta);
                    return;
                }

                person.nextDirection.facingDirection = person.pathHome.pop();
                person.state = State.WALKING;
                person.handle(delta);
                return;

            }

            // Start walking a new direction
            person.direction = person.nextDirection.facingDirection;
//            nextDirection = getRandomValidDirection((int) person.x, (int) person.y, reverseDirection(currentDirection));
            person.state = State.WALKING;
            person.handle(delta);
            return;
        }

        // Default: keep walking straight
        person.state = State.WALKING;
        person.handle(delta);

    }



    public void handlePatrollingSprite(final Person person, final float delta) {

        // Normal people can only walk on roads
        if (person.currentBuilding == null || !person.currentBuilding.name.equals("road")) {
            person.state = State.IDLE;
            person.handle(delta);
            return;
        }

        // In the 'leave-square danger zone'
        if ((person.direction == UP && person.edge > person.currentTileY + TILE_SIZE - PIXELS_AWAY_FROM_EDGE_OF_TILE)
                || (person.direction == DOWN && person.edge < person.currentTileY + PIXELS_AWAY_FROM_EDGE_OF_TILE)
                || (person.direction == RIGHT && person.edge > person.currentTileX + TILE_SIZE - PIXELS_AWAY_FROM_EDGE_OF_TILE)
                || (person.direction == LEFT && person.edge < person.currentTileX + PIXELS_AWAY_FROM_EDGE_OF_TILE)) {

            // No available adjacent road
            if (person.nextDirection == null) {

                // Check the road behind
                person.nextDirection = getRandomValidDirection(person.currentTileX, person.currentTileY, 1, null, TILE_SIZE, tilesMap);
                if (person.nextDirection == null) {
                    person.state = State.IDLE;
                    person.handle(delta);
                    return;
                }
            }

            // Keep walking straight
            if (person.nextDirection.facingDirection == person.direction) {
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
                person.nextDirection = getRandomValidDirection(person.x, person.y, person.currentBuilding.tileSize, reverseDirection(person.direction), TILE_SIZE, tilesMap);
                person.state = State.WALKING;
                person.handle(delta);
                return;

            }

            // Start walking new direction in the same square



            // Start walking a new direction
            person.direction = person.nextDirection.facingDirection;
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
