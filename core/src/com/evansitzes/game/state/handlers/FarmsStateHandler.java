package com.evansitzes.game.state.handlers;

import com.evansitzes.game.CityBuildingGame;
import com.evansitzes.game.GameScreen;
import com.evansitzes.game.buildings.Farmhouse;

import java.util.List;

/**
 * Created by evan on 2/4/17.
 */
public class FarmsStateHandler {

    private CityBuildingGame game;
    private List<Farmhouse> buildings;
    private GameScreen gameScreen;
//    private ArrayList<PatrolPerson> patrollingPersons;
//    private ArrayList<Person> returningHomePersons;

    float time;

    public FarmsStateHandler(final CityBuildingGame game, final List<Farmhouse> buildings, final GameScreen gameScreen) {
        this.game = game;
        this.buildings = buildings;
        this.gameScreen = gameScreen;
//        patrollingPersons = new ArrayList<PatrolPerson>();
//        returningHomePersons = new ArrayList<Person>();

    }

    public void handleFarms(final float delta) {
        time += delta;

        if (time > 3) {

            time = 0;
        }

    }
}
