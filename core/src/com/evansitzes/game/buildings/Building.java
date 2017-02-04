package com.evansitzes.game.buildings;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.evansitzes.game.CityBuildingGame;
import com.evansitzes.game.Textures;
import com.evansitzes.game.Textures.Sidebar;

/**
 * Created by evan on 12/14/16.
 */
public class Building {

    public CityBuildingGame game;
    public String name;
    public String prettyName;
    public String description;
    public Sprite sprite;
    public TextureRegionDrawable image;
    public BuildingType buildingType;
    public int tileSize;
    public int x;
    public int y;

    public enum BuildingType {
        RESIDENTIAL, EMPLOYABLE, OTHER
    }

    public Building() {
    }

    public Building(final CityBuildingGame game, final int tileSize, final String type) {
        this.game = game;
        image = new TextureRegionDrawable(getBuildingSprite(type));
        sprite = new Sprite(getBuildingSprite(type));
        this.tileSize = tileSize;
        sprite.setSize(32 * tileSize, 32 * tileSize);

        //TODO this is implemented terribly
        if (type.equals("house")) {
            this.buildingType = BuildingType.RESIDENTIAL;
        } else if (type.equals("employableBuilding") || type.equals("farmhouse")) {
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
    private TextureRegion getBuildingSprite(final String type) {
        if (type.equals("house")) {
            return Sidebar.HOUSE;
        }

        if (type.equals("road")) {
            return Textures.Road.VERTICLE_ROAD;
        }

        if (type.equals("farmhouse")) {
            return Sidebar.FARMHOUSE;
        }

        if (type.equals("employableBuilding")) {
            return Sidebar.GUARD_HOUSE;
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
