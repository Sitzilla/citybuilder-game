package com.evansitzes.game.people;

import com.evansitzes.game.CityBuildingGame;
import com.evansitzes.game.buildings.Building;
import com.evansitzes.game.people.Person.Facing;
import com.evansitzes.game.people.Person.State;

import java.util.ArrayList;

/**
 * Created by evan on 12/29/16.
 */
public class SpriteHandler {
    final private CityBuildingGame game;
    final private Person person;
    private static int TILE_SIZE;
    private ArrayList<Building> buildings;

    private int currentTileX;
    private int currentTileY;
    int nextTileY;

    public SpriteHandler(final CityBuildingGame game, final Person person, final int TILE_SIZE, final ArrayList<Building> buildings) {
        this.game = game;
        this.person = person;
        this.TILE_SIZE = TILE_SIZE;
        this.buildings = buildings;
    }

    public void handleSprite(final float delta) {

        person.state = State.IDLE;
        person.direction = Facing.DOWN;

        if (currentTileY != getYCornerTileFromMiddleArea((int) person.y + 1)) {
            currentTileX = getXCornerTileFromMiddleArea((int) person.x);
            currentTileY = getYCornerTileFromMiddleArea((int) person.y + 1);
            nextTileY = getYCornerTileFromMiddleArea((int) person.y) - TILE_SIZE + 1;
            System.out.println("current tile x: " + currentTileX);
            System.out.println("next tile y: " + nextTileY);
        }

        for (final Building building : buildings) {

            if (person.y > currentTileY + 1) {
                person.state = State.WALKING;
            }


            if (building.overhangs(currentTileX, nextTileY) && building.name.equals("road")) {
                person.state = State.WALKING;
                break;
            }

        }


        person.x = currentTileX + 1;
        person.handle(delta);


//        person.sprite.setPosition(385, 500);
//        person.sprite.draw(game.batch);

//        final AnimatedSprite animatedSprite = new AnimatedSprite(game);
//        animatedSprite.handle(delta);
    }



    private int getXCornerTileFromMiddleArea(final int screenX) {
            return screenX / TILE_SIZE * TILE_SIZE;
    }

    private int getYCornerTileFromMiddleArea(final int screenY) {
        return screenY / TILE_SIZE * TILE_SIZE;
    }

    public void setBuildings(final ArrayList<Building> buildings) {
        this.buildings = buildings;
    }
}
