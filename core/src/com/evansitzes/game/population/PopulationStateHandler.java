package com.evansitzes.game.population;

import com.evansitzes.game.buildings.House;

import java.util.ArrayList;

/**
 * Created by evan on 1/11/17.
 */
public class PopulationStateHandler {

    float time;
    public int totalPopulation;

    final ArrayList<House> houses;

    public PopulationStateHandler(final ArrayList<House> houses) {
        this.houses = houses;
    }

    public void handlePopulationState(final float delta) {
        time += delta;
        int newPopulation = 0;

        // Check population every 3 seconds
        if (time > 1) {
            for (final House building : houses) {
                building.handleHouse(time);
                newPopulation += building.currentPeople;
            }
            time = 0;
            totalPopulation = newPopulation;
        }
    }


}
