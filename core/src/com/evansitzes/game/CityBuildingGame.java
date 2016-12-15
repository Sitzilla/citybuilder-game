package com.evansitzes.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by evan on 12/13/16.
 */
public class CityBuildingGame extends Game {
    public Configuration config;
    public SpriteBatch batch;
    public BitmapFont font;

    private GameflowController gameflowController;

    public CityBuildingGame(final Configuration config) {
        this.config = config;
    }

    @Override
    public void create () {
        batch = new SpriteBatch();
        font = new BitmapFont(); //Use LibGDX's default Arial font.

        gameflowController = new GameflowController(this);
        gameflowController.setGameScreen();
    }

    @Override
    public void render () {
        super.render();
    }

    public void dispose() {
        batch.dispose();
        font.dispose();
    }
}
