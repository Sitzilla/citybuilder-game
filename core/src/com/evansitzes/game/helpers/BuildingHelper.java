package com.evansitzes.game.helpers;

import com.evansitzes.game.CityBuildingGame;
import com.evansitzes.game.buildings.Building;
import com.evansitzes.game.state.Definition;

import java.util.Map;

/**
 * Created by evan on 1/21/17.
 */
public class BuildingHelper {

    private static Map<String, Definition> OBJECT_DEFINITIONS;

    public BuildingHelper(final Map<String, Definition> objectDefinitions) {
        this.OBJECT_DEFINITIONS = objectDefinitions;
    }

    public static Building createGenericBuilding(final CityBuildingGame game, final String name) {
        final Building building = new Building(game, OBJECT_DEFINITIONS.get(name).getTileSize(), OBJECT_DEFINITIONS.get(name).getType());
        building.name = name;
        building.prettyName = OBJECT_DEFINITIONS.get(name).getPrettyName();
        building.description = OBJECT_DEFINITIONS.get(name).getDescription();

        return building;
    }
}
