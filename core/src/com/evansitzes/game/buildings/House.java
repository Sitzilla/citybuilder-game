package com.evansitzes.game.buildings;

import com.evansitzes.game.CityBuildingGame;

/**
 * Created by evan on 1/12/17.
 */
public class House extends Building {

    private static final float TIME_UNTIL_HOME_UPDATE = 2;

    public int peopleCapacity;
    public int currentPeople;
    float time;

    public House(final CityBuildingGame game, final int tileSize, final String name, final String prettyName) {
        super(game, tileSize, name, prettyName);
        peopleCapacity = 8;
        currentPeople = 0;
        time = 0;
    }

    public void handleHouse(float delta) {
        time += delta;

        if (time > 2) {
            if (currentPeople < peopleCapacity) {
                currentPeople++;
            }

            time = 0;
        }

    }



}
