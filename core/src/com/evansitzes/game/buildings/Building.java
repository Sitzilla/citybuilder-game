package com.evansitzes.game.buildings;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.evansitzes.game.CityBuildingGame;
import com.evansitzes.game.Configuration;

/**
 * Created by evan on 12/14/16.
 */
public class Building extends Rectangle {

    public final CityBuildingGame game;
    public String name;
    public Sprite sprite;
    public final Vector3 position = new Vector3();
    public Rectangle rectangle;
    public int tileSize;

    public Building(final CityBuildingGame game, final TextureRegion buildingSprite, final int tileSize) {
        this.game = game;
        rectangle = new Rectangle();
        sprite = new Sprite(buildingSprite);
        this.tileSize = tileSize;
        sprite.setSize(Configuration.WIDTH_MODIFIER * 32 * tileSize, Configuration.HEIGHT_MODIFIER * 32 * tileSize);
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

    public void draw() {
        // TODO should I be setting position off the sprites position or the rectangles?
        sprite.setPosition(this.x, this.y);
        this.rectangle.set(this.x + 20, this.y + 20, 10, 10);
//            sprite.setPosition(this.rectangle.x, this.rectangle.y);
//            this.rectangle.set(this.rectangle.x + 20, this.rectangle.y + 20, 10, 10);
        sprite.draw(game.batch);
    }

    public void locate(final float x, final float y) {
        this.x = x;
        this.y = y;
    }

    public void setSpritesPositions() {}
}
