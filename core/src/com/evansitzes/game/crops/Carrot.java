package com.evansitzes.game.crops;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.evansitzes.game.Textures.Crops;

/**
 * Created by evan on 2/4/17.
 */
public class Carrot extends Crop {

    public Carrot() {
        this.image = new TextureRegionDrawable(Crops.CARROTS_IN_GROUND);
        this.sprite = new Sprite(Crops.CARROTS_IN_GROUND);
    }
}
