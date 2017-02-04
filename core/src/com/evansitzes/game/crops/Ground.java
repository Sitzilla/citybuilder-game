package com.evansitzes.game.crops;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.evansitzes.game.Textures;

/**
 * Created by evan on 2/4/17.
 */
public class Ground extends Crop {

    public Ground() {
        this.image = new TextureRegionDrawable(Textures.Crops.GROUND);
        this.sprite = new Sprite(Textures.Crops.GROUND);
    }
}
