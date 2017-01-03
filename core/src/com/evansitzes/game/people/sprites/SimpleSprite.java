package com.evansitzes.game.people.sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.evansitzes.game.CityBuildingGame;

/**
 * Created by evan on 12/29/16.
 */
public class SimpleSprite extends Entity {

    public final Sprite sprite;

    public SimpleSprite(final CityBuildingGame game, final TextureRegion texture) {
        super(game);
        sprite = new Sprite(texture);
    }

    public void handle(float delta) {
        sprite.setPosition(x, y);
        sprite.draw(game.batch);
    }
}
