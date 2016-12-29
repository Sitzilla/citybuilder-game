package com.evansitzes.game.people;

import com.badlogic.gdx.math.Rectangle;
import com.evansitzes.game.CityBuildingGame;

/**
 * Created by evan on 12/29/16.
 */
public class Entity extends Rectangle {

    public final CityBuildingGame game;
    public Rectangle rectangle;

    public Entity(final CityBuildingGame game) {
        this.game = game;
        rectangle = new Rectangle();
    }

    public void locate(final float x, final float y) {
        this.x = x;
        this.y = y;
    }
}
