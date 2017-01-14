package com.evansitzes.game.buildings;

import com.evansitzes.game.CityBuildingGame;

/**
 * Created by evan on 1/12/17.
 */
public class EmployableBuilding extends Building {

    private static final float TIME_UNTIL_BUILDING_UPDATE = 2;

    public int maxEmployability;
    public int currentlyEmployed;
    float time;

    public EmployableBuilding(final CityBuildingGame game, final int tileSize, final String name, final String prettyName) {
        super(game, tileSize, name, prettyName);
        maxEmployability = 5;
    }

    public void handleBuilding(final int amountToEmploy, final float delta) {
        time += delta;

        if (amountToEmploy > maxEmployability) {
            currentlyEmployed = maxEmployability;
        } else {
            currentlyEmployed = amountToEmploy;
        }

        System.out.println("Currently Employed: " + currentlyEmployed);
        if (time > TIME_UNTIL_BUILDING_UPDATE) {
            time = 0;
        }

    }
}
