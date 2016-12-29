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

    private TextureRegion currentFrame;
    private TextureRegion[] downWalking;
    private TextureRegion[] currentWalking;

    private float stateTime;
    private boolean looping = true;
    private float animationSpeed = 1f/5f;

    public AnimatedSprite(CityBuildingGame game) {
        super(game);
        downWalking = Textures.People.WALKING_DOWN;
        this.game = game;

        currentAnimation = new Animation(animationSpeed, downWalking);
    }

    public void handle(float delta) {
        stateTime += delta;
        currentAnimation = new Animation(animationSpeed, currentWalking);
        currentFrame = currentAnimation.getKeyFrame(stateTime, looping);

//        this.

        game.batch.draw(currentFrame, x, y);
    }

    public void setCurrentDirection(Person.Facing direction) {
        if (direction == Person.Facing.LEFT) {
//            currentWalking = leftWalking;
        } else if (direction == Person.Facing.RIGHT) {
//            currentWalking = rightWalking;
//        } else if (direction == Person.Facing.UP) {
//            currentWalking = upWalking;
        } else if (direction == Person.Facing.DOWN) {
            currentWalking = downWalking;
        }
    }

}
