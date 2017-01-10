package com.evansitzes.game.people.sprites;

import com.evansitzes.game.CityBuildingGame;
import com.evansitzes.game.GameScreen;
import com.evansitzes.game.buildings.Building;
import org.joda.time.DateTime;

/**
 * Created by evan on 1/2/17.
 */
public class PatrolPerson extends Person {

    public DateTime timeCreated;
    public boolean timeInFieldExpired;

    // All sprite time in seconds
    public float maxTimeInField;
    public float currentTimeInField;

    public PatrolPerson(final CityBuildingGame game, final GameScreen screen, final String name, final Building homeBuilding, final float maxTimeInField, final int x, final int y) {
        super(game, screen, name, homeBuilding, x, y);

        this.maxTimeInField = maxTimeInField;
        timeCreated = new DateTime();
        timeInFieldExpired = false;
    }

    public void updateTimeInField(final float delta) {
        currentTimeInField += delta;

        if (currentTimeInField > maxTimeInField) {
            timeInFieldExpired = true;
        }
    }


}
