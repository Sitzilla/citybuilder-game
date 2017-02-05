package com.evansitzes.game.crops;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.evansitzes.game.CityBuildingGame;
import com.evansitzes.game.Textures;

/**
 * Created by evan on 2/4/17.
 */
public class SeededCarrot extends Crop {

    public SeededCarrot(final CityBuildingGame game) {
        super(game);
        // TODO move all of this to Crop
        this.image = new TextureRegionDrawable(Textures.Crops.CARROTS_IN_GROUND);
        this.sprite = new Sprite(Textures.Crops.CARROTS_IN_GROUND);
        this.tileSize = 1;
        sprite.setSize(32 * tileSize, 32 * tileSize);
    }
}

