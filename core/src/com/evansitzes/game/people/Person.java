package com.evansitzes.game.people;

import com.evansitzes.game.CityBuildingGame;
import com.evansitzes.game.Configuration;
import com.evansitzes.game.GameScreen;
import com.evansitzes.game.Textures;

import static com.evansitzes.game.people.Person.Facing.DOWN;
import static com.evansitzes.game.people.Person.State.IDLE;

/**
 * Created by evan on 12/29/16.
 */
public class Person extends Entity {

    public static final int MOMENT_SPEED = 1;

    private final GameScreen screen;
    public SimpleSprite currentSprite;
    public AnimatedSprite animatedSprite;
    Configuration configuration;

    public State state;

    public enum State {
        IDLE, WALKING
    }

    public enum Facing {
        LEFT, RIGHT, UP, DOWN
    }

    public Facing direction;


    public Person(final CityBuildingGame game, final GameScreen screen) {
        super(game);
        this.screen = screen;
        this.state = IDLE;
        direction = DOWN;
//        configuration = new Configuration();

        currentSprite = new SimpleSprite(game, Textures.People.STANDING);
        animatedSprite = new AnimatedSprite(game);


//        position.x = new Configuration().STARTING_POSITION_X;
//        position.y = new Configuration().STARTING_POSITION_Y;
        locate(385, 500);
        animatedSprite.locate(385, 500);
        currentSprite.locate(385, 500);
//        animatedSprite.position.set(385, 500, 0);
//        currentSprite.position.set(385, 500, 0);

//        this.position.set(700, 700, 0);
//        rectangle = currentSprite.rectangle;

    }

//    @Override
    public void handle(final float delta) {

        // handle motion
        handleInput(delta);

        if (state == IDLE) {
            currentSprite.handle(delta);
        }
        else if (state == State.WALKING) {

            animatedSprite.setCurrentDirection(direction);

//            if (direction == RIGHT) {
//                position.x += MOMENT_SPEED;
//            } else if (direction == LEFT) {
//                position.x -= MOMENT_SPEED;
//            } if (direction == UP) {
//                position.y += MOMENT_SPEED;
//            } else if (direction == DOWN) {
            if (direction == DOWN) {
                y -= MOMENT_SPEED;
            }

//            this.rectangle.set(x, y, 30, 30);
            locate(x, y);
            animatedSprite.locate(x, y);
            currentSprite.locate(x, y);

            animatedSprite.handle(delta);
            state = IDLE;
        }
    }

    private void handleInput(final float delta) {

    }

    private void checkBounds() {
    }
}
