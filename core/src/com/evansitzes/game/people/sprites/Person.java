package com.evansitzes.game.people.sprites;

import com.evansitzes.game.CityBuildingGame;
import com.evansitzes.game.Configuration;
import com.evansitzes.game.GameScreen;
import com.evansitzes.game.Textures;
import com.evansitzes.game.buildings.Building;
import com.evansitzes.game.helpers.Direction;

import java.util.Stack;

import static com.evansitzes.game.people.sprites.Person.Facing.DOWN;
import static com.evansitzes.game.people.sprites.Person.Facing.RIGHT;
import static com.evansitzes.game.people.sprites.Person.State.IDLE;

/**
 * Created by evan on 12/29/16.
 */
public class Person extends Entity {

    public static final int MOMENT_SPEED = 1;

    private final GameScreen screen;
    public SimpleSprite currentSprite;
    public AnimatedSprite animatedSprite;
    public Building homeBuilding;
    Configuration configuration;


    public int edge;
    public State state;
    public String name;

    public Building currentBuilding;
    public Direction nextDirection;
    public int currentTileX;
    public int currentTileY;

    public Stack<Facing> pathHome;
    public boolean justBeganReturningHome;

    public enum State {
        IDLE, WALKING
    }

    public enum Facing {
        LEFT, RIGHT, UP, DOWN
    }

    public Facing direction;


    public Person(final CityBuildingGame game, final GameScreen screen, final String name, final Building homeBuilding, final int x, final int y) {
        super(game);
        this.name = name;
        this.screen = screen;
        this.state = IDLE;
        this.homeBuilding = homeBuilding;
        direction = DOWN;
//        configuration = new Configuration();

        currentSprite = new SimpleSprite(game, Textures.People.loadStandingSprite(name));
        animatedSprite = new AnimatedSprite(game, name);


//        position.x = new Configuration().STARTING_POSITION_X;
//        position.y = new Configuration().STARTING_POSITION_Y;
        locate(x, y);
        animatedSprite.locate(x, y);
        currentSprite.locate(x, y);
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

            //TODO magic numbers + casts
            if (direction == RIGHT) {
                x += MOMENT_SPEED;
                edge = x + 30;
            } else if (direction == Facing.LEFT) {
                x -= MOMENT_SPEED;
                edge = x + 3;
            } if (direction == Facing.UP) {
                y += MOMENT_SPEED;
                edge = y + 13;
            } else if (direction == DOWN) {
                y -= MOMENT_SPEED;
                edge = y;
            }

//            this.rectangle.set(x, y, 30, 30);
            locate(x, y);
            animatedSprite.locate(x, y);
            currentSprite.locate(x, y);

            animatedSprite.handle(delta);
            state = IDLE;
        }
    }
//
//    //TODO maybe make these abstract?
//    public boolean hasTimeInFieldExpired() {
//        return false;
//    }
//
//    public void setTimeInFieldHasExpired() {
//    }

    private void handleInput(final float delta) {

    }

}
