package com.evansitzes.game.crops;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.evansitzes.game.CityBuildingGame;
import com.evansitzes.game.Textures.Crops;

/**
 * Created by evan on 2/4/17.
 */
public class Ground extends Crop {

    public Ground(final CityBuildingGame game) {
        super(game);

        // TODO move all of this to Crop
        this.image = new TextureRegionDrawable(Crops.GROUND);
        this.sprite = new Sprite(Crops.GROUND);
        this.tileSize = 1;
        sprite.setSize(32 * tileSize, 32 * tileSize);
    }
}
