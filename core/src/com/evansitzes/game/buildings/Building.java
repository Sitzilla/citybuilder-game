package com.evansitzes.game.buildings;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.evansitzes.game.CityBuildingGame;
import com.evansitzes.game.Textures;

/**
 * Created by evan on 12/14/16.
 */
public class Building extends Rectangle {

    public final CityBuildingGame game;
    public String name;
    public Sprite sprite;
    public final Vector3 position = new Vector3();
    public Rectangle rectangle;

    public Building(final CityBuildingGame game) {
        this.game = game;
        rectangle = new Rectangle();
        sprite = new Sprite(Textures.Sidebar.HOUSE);
    }

//    public boolean overlaps(final Entity entity) {
//        if (rectangle == null) { return false; }
//
//        return rectangle.overlaps(entity.rectangle);
//    }

//    public boolean wouldOverlap(final Entity entity, PlayerSprite.Facing direction) {
//        if (rectangle == null) { return false; }
//
//        return rectangle.overlaps(entity.rectangle);
//    }

    public void locate(final float x, final float y) {
        this.x = x;
        this.y = y;
    }

    public void setSpritesPositions() {}
}
