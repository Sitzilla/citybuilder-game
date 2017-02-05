package com.evansitzes.game.state.handlers;

import com.evansitzes.game.CityBuildingGame;
import com.evansitzes.game.GameScreen;
import com.evansitzes.game.buildings.EmployableBuilding;
import com.evansitzes.game.people.sprites.Farmer;
import com.evansitzes.game.people.sprites.PatrolPerson;
import com.evansitzes.game.people.sprites.Person;
import com.evansitzes.game.people.sprites.Person.State;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by evan on 12/30/16.
 */
public class SpriteStateHandler {

    private CityBuildingGame game;
    private List<EmployableBuilding> buildings;
    private GameScreen gameScreen;
    private List<PatrolPerson> patrollingPersons;
    private List<Farmer> harvestingPersons;
    private List<Person> returningHomePersons;

    float time;

    public SpriteStateHandler(final CityBuildingGame game, final List<EmployableBuilding> buildings, final GameScreen gameScreen) {
        this.game = game;
        this.buildings = buildings;
        this.gameScreen = gameScreen;
        patrollingPersons = new ArrayList<PatrolPerson>();
        harvestingPersons = new ArrayList<Farmer>();
        returningHomePersons = new ArrayList<Person>();
    }

    public void handleSpritesStates(final float delta) {
        time += delta;

        // Check sprite state every 1 second
        if (time > 1) {
            for (final EmployableBuilding building : buildings) {
                if (building.name.equals("guard_house") && building.spritesInField < building.currentSpritesCapacity && building.isConnectedToRoad) {
                    building.spritesInField++;
                    gameScreen.createNewSprite("guard", building.x, building.y, building);
                }

                if (building.name.equals("farmhouse") && building.spritesInField < building.currentSpritesCapacity && building.isConnectedToRoad) {
                    building.spritesInField++;
                    gameScreen.createNewSprite("farmer", building.x, building.y, building);
                }
            }

            // Check sprites going home
            final Iterator<Person> returningIterator = returningHomePersons.iterator();
            while (returningIterator.hasNext()) {
                final Person person = returningIterator.next();

                if (person.pathHome.isEmpty()) {
                    person.homeBuilding.spritesInField--;
                    returningIterator.remove();
                }

            }

            // Update time for all sprites in field
            for (final PatrolPerson person : patrollingPersons) {
                person.updateTimeInField(time);
            }
            for (final Farmer person : harvestingPersons) {

                if (person.state == State.WORKING) {
                    person.updateWorkingTime(time);
                }

                person.updateTimeInField(time);
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

            // Check sprites harvesting
            final Iterator<Farmer> harvestingIterator = harvestingPersons.iterator();

            while (harvestingIterator.hasNext()) {
                final Farmer person = harvestingIterator.next();

                if (person.timeInFieldExpired) {
                    // TODO send farmer home
//                    returningHomePersons.add(person);
//                    patrollingIterator.remove();
//                    gameScreen.sendSpriteHome(person);
                }

            }

            time = 0;
        }
    }

    public void addSpriteToList(final Person person) {
        patrollingPersons.add((PatrolPerson) person);
    }

    public void addFarmerToList(final Farmer person) {
        harvestingPersons.add(person);
    }

    public void addSpriteToReturningHomeList(final Person person) {
        returningHomePersons.add(person);
        patrollingPersons.remove(person);
    }

    public List<PatrolPerson> getPatrollingPersons() {
        return patrollingPersons;
    }

    public List<Person> getReturningHomePersons() {
        return returningHomePersons;
    }

    public List<Farmer> getHarvestingPersons() {
        return harvestingPersons;
    }
}
