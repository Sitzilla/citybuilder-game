package com.evansitzes.game.people;

import com.evansitzes.game.CityBuildingGame;

/**
 * Created by evan on 12/29/16.
 */
public class Entity {

    public final CityBuildingGame game;
    public int x;
    public int y;

    public Entity(final CityBuildingGame game) {
        this.game = game;
    }

    public void locate(final int x, final int y) {
        this.x = x;
        this.y = y;
    }
}
