package com.evansitzes.game.state.handlers;

import com.evansitzes.game.buildings.EmployableBuilding;

import java.util.ArrayList;

/**
 * Created by evan on 1/14/17.
 */
public class EmploymentStateHandler {

    private static final float LABOR_FORCE_PARTICIPATION_RATE = 0.6f;

    float time;
    public int totalEmployed;
    public int maxEmployable;
    public int totalLaborForce;

    final ArrayList<EmployableBuilding> buildings;

    public EmploymentStateHandler(final ArrayList<EmployableBuilding> buildings) {
        this.buildings = buildings;
    }

    public void handleEmploymentState(final float delta, final int totalPopulation) {
        time += delta;
        int newEmployed = 0;
        int newMaxEmployed = 0;

        // Check employment every 3 second
        if (time > 3) {
            totalLaborForce = (int) (totalPopulation * LABOR_FORCE_PARTICIPATION_RATE);

            // Spread labor evenly through employable buildings
            final float buildingEmployablePercentage = totalLaborForce / (float) maxEmployable;

            for (final EmployableBuilding building : buildings) {
                final int amountToEmploy = (int) (building.maxEmployability * buildingEmployablePercentage);

                building.handleBuilding(amountToEmploy, time);
                newEmployed += building.currentlyEmployed;
                newMaxEmployed += building.maxEmployability;
            }
            System.out.println("---------------");
            time = 0;
            totalEmployed = newEmployed;
            maxEmployable = newMaxEmployed;
        }
    }
}
