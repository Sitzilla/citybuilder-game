package com.evansitzes.game.people;

import com.evansitzes.game.CityBuildingGame;
import com.evansitzes.game.GameScreen;
import com.evansitzes.game.buildings.Building;

import java.util.ArrayList;

/**
 * Created by evan on 12/30/16.
 */
public class SpriteStateHandler {

    private CityBuildingGame game;
    private ArrayList<Building> buildings;
    private GameScreen gameScreen;

    public SpriteStateHandler(final CityBuildingGame game, final ArrayList<Building> buildings, final GameScreen gameScreen) {
        this.game = game;
        this.buildings = buildings;
        this.gameScreen = gameScreen;
    }

    public void handleSpritesStates() {
        for (final Building building : buildings) {
            if (building.name.equals("guard_house") && building.spritesGenerated < 1) {
                building.spritesGenerated++;
                gameScreen.createNewSprite("guard", (int) building.x, (int) building.y, building.tileSize);
            }
        }
    }

}
