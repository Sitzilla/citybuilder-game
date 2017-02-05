package com.evansitzes.game.people.sprites;

import com.evansitzes.game.CityBuildingGame;
import com.evansitzes.game.GameScreen;
import com.evansitzes.game.buildings.EmployableBuilding;
import com.evansitzes.game.buildings.Farmhouse;

/**
 * Created by evan on 2/4/17.
 */
public class Farmer extends PatrolPerson {

    public Farmhouse homeFarm;

    // All sprite time in seconds
    public float maxWorkingTime;
    public float currentWorkingTime;

    // Experimental state variables
    public boolean enteringFinalStage;
    public boolean isInFinalSquare;

    public Farmer(final CityBuildingGame game, final GameScreen screen, final String name, final EmployableBuilding homeBuilding, final float maxTimeInField, final int x, final int y) {
        super(game, screen, name, homeBuilding, maxTimeInField, x, y);
        maxWorkingTime = 3;
        this.homeFarm = (Farmhouse) homeBuilding;
    }


    public void updateWorkingTime(final float delta) {

        if (currentWorkingTime == 0) {
            homeFarm.beginCultivatingLand(currentTileX, currentTileY);
        }

        currentWorkingTime += delta;

        if (currentWorkingTime > maxWorkingTime) {
            currentWorkingTime = 0;
            state = State.IDLE;
        }
    }
}
