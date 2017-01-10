package com.evansitzes.game.people;

import com.evansitzes.game.CityBuildingGame;
import com.evansitzes.game.GameScreen;
import com.evansitzes.game.buildings.Building;
import com.evansitzes.game.people.sprites.PatrolPerson;
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
    private ArrayList<PatrolPerson> patrollingPersons;
    private ArrayList<Person> returningHomePersons;

    public SpriteStateHandler(final CityBuildingGame game, final ArrayList<Building> buildings, final GameScreen gameScreen) {
        this.game = game;
        this.buildings = buildings;
        this.gameScreen = gameScreen;
        patrollingPersons = new ArrayList<PatrolPerson>();
        returningHomePersons = new ArrayList<Person>();

    }

    public void handleSpritesStates(final float delta) {

        //TODO no need to check this 3 times a second
        for (final Building building : buildings) {
            if (building.name.equals("guard_house") && building.spritesGenerated < 1) {
                building.spritesGenerated++;
                gameScreen.createNewSprite("guard", building.x, building.y, building);
            }
        }

        // Check sprites going home
        final Iterator<Person> returningIterator = returningHomePersons.iterator();
        while (returningIterator.hasNext()) {
            final Person person = returningIterator.next();

            if (person.pathHome.isEmpty()) {
                returningIterator.remove();
            }

        }

        // Update time for all sprites in field
        for (final PatrolPerson person : patrollingPersons) {
            person.updateTimeInField(delta);
        }

        // Check sprites patrolling
        final Iterator<PatrolPerson> patrollingIterator = patrollingPersons.iterator();

        while (patrollingIterator.hasNext()) {
            final PatrolPerson person = patrollingIterator.next();

            if (person.timeInFieldExpired) {
                returningHomePersons.add(person);
                patrollingIterator.remove();
                gameScreen.sendSpriteHome(person);
            }

        }
    }

    public void addSpriteToList(final Person person) {
        patrollingPersons.add((PatrolPerson) person);
    }

    public void addSpriteToReturningHomeList(final Person person) {
        returningHomePersons.add(person);
        patrollingPersons.remove(person);
    }

    public ArrayList<PatrolPerson> getPatrollingPersons() {
        return patrollingPersons;
    }

    public ArrayList<Person> getReturningHomePersons() {
        return returningHomePersons;
    }
}
