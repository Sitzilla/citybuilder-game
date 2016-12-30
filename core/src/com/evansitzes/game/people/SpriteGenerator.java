package com.evansitzes.game.people;

import com.evansitzes.game.CityBuildingGame;
import com.evansitzes.game.GameScreen;

/**
 * Created by evan on 12/30/16.
 */
public class SpriteGenerator {

    public static Person generatePerson(final CityBuildingGame game, final GameScreen screen) {
        final Person person = new Person(game, screen);

        return person;
    }

}
