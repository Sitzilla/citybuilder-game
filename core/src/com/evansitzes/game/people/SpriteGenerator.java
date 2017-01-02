package com.evansitzes.game.people;

import com.evansitzes.game.CityBuildingGame;
import com.evansitzes.game.GameScreen;
import com.evansitzes.game.environment.TilesMap;

import static com.evansitzes.game.people.Person.Facing.DOWN;

/**
 * Created by evan on 12/30/16.
 */
public class SpriteGenerator {

    public static Person generatePerson(final CityBuildingGame game, final GameScreen screen, final int TILE_SIZE, final TilesMap tilesMap, final String name, final int x, final int y) {
        final Person person = new Person(game, screen, name, x, y);
        person.state = Person.State.IDLE;
        person.direction = DOWN;

        // initialize of sprite phase
        person.currentTileX = SpriteHelper.getXCornerTileFromMiddleArea(person.x, TILE_SIZE);
        person.currentTileY = SpriteHelper.getYCornerTileFromMiddleArea(person.y, TILE_SIZE);
        person.currentBuilding = tilesMap.getTile(person.currentTileX / TILE_SIZE, person.currentTileY / TILE_SIZE).getBuilding();
        // TODO hardcoded to down
        //TODO remove Rectangle extension so we dont have to cast to ints everywhere
        person.nextDirection = SpriteHelper.getRandomValidDirection(person.x, person.y, 1, null, TILE_SIZE, tilesMap);

        //TODO alert message to player
        if (person.currentBuilding == null || !person.currentBuilding.name.equals("road")) {
            System.out.println("Building isnt connected to a road!!");
            return null;
        }

        return person;
    }

}
