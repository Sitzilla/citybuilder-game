package com.evansitzes.game.people;

import com.evansitzes.game.CityBuildingGame;

/**
 * Created by evan on 12/29/16.
 */
public class SpriteHandler {
    final private CityBuildingGame game;
    final private Person person;

    public SpriteHandler(final CityBuildingGame game, final Person person) {
        this.game = game;
        this.person = person;
    }

    public void handleSprite(final float delta) {

        person.state = Person.State.IDLE;
        person.direction = Person.Facing.DOWN;
        person.handle(delta);

//        person.sprite.setPosition(385, 500);
//        person.sprite.draw(game.batch);

//        final AnimatedSprite animatedSprite = new AnimatedSprite(game);
//        animatedSprite.handle(delta);
    }
}
