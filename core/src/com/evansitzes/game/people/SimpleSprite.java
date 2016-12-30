package com.evansitzes.game.people;

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
//        rectangle = new Rectangle();
//        rectangle.width = sprite.getWidth();
//        rectangle.height = sprite.getHeight();

    }

//    @Override
    public void handle(float delta) {
        sprite.setPosition(x, y);
        sprite.draw(game.batch);
    }
}
