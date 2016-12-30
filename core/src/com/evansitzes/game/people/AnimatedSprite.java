package com.evansitzes.game.people;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.evansitzes.game.CityBuildingGame;
import com.evansitzes.game.Textures;

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

    public AnimatedSprite(final CityBuildingGame game) {
        super(game);
        upWalking = Textures.People.WALKING_UP;
        downWalking = Textures.People.WALKING_DOWN;
        rightWalking = Textures.People.WALKING_RIGHT;
        leftWalking = Textures.People.WALKING_LEFT;

        this.game = game;

        currentAnimation = new Animation(animationSpeed, downWalking);
    }

    public void handle(final float delta) {
        stateTime += delta;
        currentAnimation = new Animation(animationSpeed, currentWalking);
        currentFrame = currentAnimation.getKeyFrame(stateTime, looping);

        game.batch.draw(currentFrame, x, y);
    }

    public void setCurrentDirection(final Person.Facing direction) {
        if (direction == Person.Facing.LEFT) {
            currentWalking = leftWalking;
        } else if (direction == Person.Facing.RIGHT) {
            currentWalking = rightWalking;
        } else if (direction == Person.Facing.UP) {
            currentWalking = upWalking;
        } else if (direction == Person.Facing.DOWN) {
            currentWalking = downWalking;
        }
    }

}
