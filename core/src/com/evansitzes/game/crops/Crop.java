package com.evansitzes.game.crops;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.evansitzes.game.CityBuildingGame;

/**
 * Created by evan on 2/4/17.
 */
public class Crop {

    public CityBuildingGame game;
    public String name;
    public String prettyName;
    public String description;
    public TextureRegionDrawable image;
    public Sprite sprite;
    public int tileSize;
    public int x;
    public int y;

    public Crop(final CityBuildingGame game) {
        this.game = game;


    }

    public void draw() {
        sprite.setPosition(this.x, this.y);
        sprite.draw(game.batch);
    }
}
