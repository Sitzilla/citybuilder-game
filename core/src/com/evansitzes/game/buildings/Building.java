package com.evansitzes.game.buildings;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.evansitzes.game.CityBuildingGame;
import com.evansitzes.game.Textures;

/**
 * Created by evan on 12/14/16.
 */
public class Building {

    public final CityBuildingGame game;
    public String name;
    public String prettyName;
    public Sprite sprite;
    public BuildingType buildingType;
    public int tileSize;
    public int x;
    public int y;

    public enum BuildingType {
        RESIDENTIAL, EMPLOYABLE, OTHER
    }

    public Building(final CityBuildingGame game, final int tileSize, final String name, final String prettyName) {
        this.game = game;
        sprite = new Sprite(getBuildingSprite(name));
        this.tileSize = tileSize;
        sprite.setSize(32 * tileSize, 32 * tileSize);
        this.name = name;
        this.prettyName = prettyName;

        //TODO this is implemented terribly
        if (name.equals("house")) {
            this.buildingType = BuildingType.RESIDENTIAL;
        } else if (name.equals("guard_house")) {
            this.buildingType = BuildingType.EMPLOYABLE;
        } else {
            this.buildingType = BuildingType.OTHER;
        }
    }

    public void draw() {
        sprite.setPosition(this.x, this.y);
        sprite.draw(game.batch);
    }

    //TODO replace this with some kind of configuration lookup
    private TextureRegion getBuildingSprite(final String name) {
        if (name.equals("house")) {
            return Textures.Sidebar.HOUSE;
        }

        if (name.equals("road")) {
            return Textures.Road.VERTICLE_ROAD;
        }

        if (name.equals("guard_house")) {
            return Textures.Sidebar.GUARD_HOUSE;
        }

        //TODO should throw an error
        return null;
    }

    public boolean overhangs(final int cornerX, final int cornerY) {
        //TODO magic numbers
        return cornerX >= x
            && cornerX < x + 32 * tileSize
            && cornerY >= y
            && cornerY < y + 32 * tileSize;

    }
}
