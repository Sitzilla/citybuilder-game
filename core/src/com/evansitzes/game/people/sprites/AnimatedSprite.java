package com.evansitzes.game.people.sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.evansitzes.game.CityBuildingGame;
import com.evansitzes.game.Textures.People;
import com.evansitzes.game.people.sprites.Person.Facing;

/**
 * Created by evan on 12/29/16.
 */
public class AnimatedSprite extends Entity {
    final CityBuildingGame game;

    private Animation currentAnimation;

    private final TextureRegion[] upWalking;
    private final TextureRegion[] downWalking;
    private final TextureRegion[] rightWalking;
    private final TextureRegion[] leftWalking;
    private TextureRegion[] currentWalking;
    private TextureRegion currentFrame;

    private float stateTime;
    private final boolean looping = true;
    private final float animationSpeed = 1f/5f;

    public AnimatedSprite(final CityBuildingGame game, final String name) {
        super(game);
        upWalking = People.loadWalkingUp(name);
        downWalking = People.loadWalkingDown(name);
        rightWalking = People.loadWalkingRight(name);
        leftWalking = People.loadWalkingLeft(name);

        this.game = game;

        currentAnimation = new Animation(animationSpeed, downWalking);
    }

    public void handle(final float delta) {
        stateTime += delta;
        currentAnimation = new Animation(animationSpeed, currentWalking);
        currentFrame = currentAnimation.getKeyFrame(stateTime, looping);

        game.batch.draw(currentFrame, x, y);
    }

    public void setCurrentDirection(final Facing direction) {
        if (direction == Facing.LEFT) {
            currentWalking = leftWalking;
        } else if (direction == Facing.RIGHT) {
            currentWalking = rightWalking;
        } else if (direction == Facing.UP) {
            currentWalking = upWalking;
        } else if (direction == Facing.DOWN) {
            currentWalking = downWalking;
        }
    }

}
