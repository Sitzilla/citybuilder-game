package com.evansitzes.game.people.sprites;

import com.evansitzes.game.CityBuildingGame;
import com.evansitzes.game.GameScreen;
import com.evansitzes.game.buildings.Building;
import org.joda.time.DateTime;

/**
 * Created by evan on 1/2/17.
 */
public class PatrolPerson extends Person {

    public long millisecondsInTheField;
    public DateTime timeCreated;
    public boolean timeInFieldExpired;

    public PatrolPerson(final CityBuildingGame game, final GameScreen screen, final String name, final Building homeBuilding, final long millisecondsInTheField, final int x, final int y) {
        super(game, screen, name, homeBuilding, x, y);

        this.millisecondsInTheField = millisecondsInTheField;
        timeCreated = new DateTime();
        timeInFieldExpired = false;
    }

    //TODO this doesnt account for game pausing
    @Override
    public boolean hasTimeInFieldExpired() {
        if (timeInFieldExpired == true) {
            return timeInFieldExpired;
        }

        return new DateTime().getMillis() > timeCreated.getMillis() + millisecondsInTheField;
    }

    @Override
    public void setTimeInFieldHasExpired() {
        timeInFieldExpired = true;
    }

}
