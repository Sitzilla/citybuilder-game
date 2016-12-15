package com.evansitzes.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

/**
 * Created by evan on 12/13/16.
 */
public class GameflowController implements ApplicationListener {

    private final CityBuildingGame game;
    private final GameScreen gameScreen;

    public GameflowController(final CityBuildingGame game) {
        this.game = game;
        gameScreen = new GameScreen(game, this);
    }

    public void setGameScreen() {
        game.setScreen(gameScreen);
    }

    @Override
    public void create() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0.0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    }

    @Override
    public void resize(final int width, final int height) {

    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

}
