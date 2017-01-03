package com.evansitzes.game.people;

import com.evansitzes.game.CityBuildingGame;
import com.evansitzes.game.GameScreen;
import com.evansitzes.game.buildings.Building;
import com.evansitzes.game.people.sprites.Person;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by evan on 12/30/16.
 */
public class SpriteStateHandler {

    private CityBuildingGame game;
    private ArrayList<Building> buildings;
    private GameScreen gameScreen;
    private ArrayList<Person> patrollingPersons;
    private ArrayList<Person> returningHomePersons;

    public SpriteStateHandler(final CityBuildingGame game, final ArrayList<Building> buildings, final GameScreen gameScreen) {
        this.game = game;
        this.buildings = buildings;
        this.gameScreen = gameScreen;
        patrollingPersons = new ArrayList<Person>();
        returningHomePersons = new ArrayList<Person>();

    }

    public void handleSpritesStates() {
        for (final Building building : buildings) {
            if (building.name.equals("guard_house") && building.spritesGenerated < 1) {
                building.spritesGenerated++;
                gameScreen.createNewSprite("guard", building.x, building.y, building);
            }
        }
    }

    public void addSpriteToList(final Person person) {
        patrollingPersons.add(person);
    }

    public ArrayList<Person> getPatrollingPersons() {
        final Iterator<Person> iterator = patrollingPersons.iterator();

        while (iterator.hasNext()) {
            final Person person = iterator.next();

            if (person.hasTimeInFieldExpired()) {
                person.setTimeInFieldHasExpired();
                returningHomePersons.add(person);
                iterator.remove();
            }

        }

        return patrollingPersons;
    }

    public ArrayList<Person> getReturningHomePersons() {
        return returningHomePersons;
    }
}
