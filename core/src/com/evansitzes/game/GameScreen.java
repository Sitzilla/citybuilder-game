package com.evansitzes.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;


/**
 * Created by evan on 12/13/16.
 */
public class GameScreen extends ApplicationAdapter implements Screen, InputProcessor {

    private Texture img;
    private TiledMap tiledMap;
    private OrthographicCamera camera;
    private TiledMapRenderer tiledMapRenderer;
    private Level level;

    private TextureRegion sidebar;
    private TextureRegionDrawable sidebarDrawable;

    private Skin skin;
    private Stage stage;
    Label fpsLabel;


    private final CityBuildingGame game;
    private final GameflowController gameflowController;

    public GameScreen(final CityBuildingGame game, final GameflowController gameflowController) {
        this.game = game;
        this.gameflowController = gameflowController;

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("skins/uiskin.json"));

        final float w = Gdx.graphics.getWidth();
        final float h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false,w,h);
        camera.update();
//        tiledMap = new TmxMapLoader().load("maps/basic-level1.tmx");
        this.level = TmxLevelLoader.load(Vector2.Zero, game, this, "battle-map");

        this.tiledMapRenderer = new OrthogonalTiledMapRenderer(level.map);
        tiledMapRenderer.setView(camera);
        Gdx.input.setInputProcessor(this);

        sidebar = Textures.Sidebar.SIDEBAR;
        sidebarDrawable = new TextureRegionDrawable(sidebar);

        final Label nameLabel = new Label("Name:", skin);
        final TextField nameText = new TextField("", skin);
        final Label addressLabel = new Label("Address:", skin);
        final TextField addressText = new TextField("", skin);

        final Table table = new Table();
//        table.setFillParent(true);
        table.setBackground(sidebarDrawable);

        table.setDebug(true); // turn on all debug lines (table, cell, and widget)
        table.setHeight(h);
        table.setWidth(300);
        table.setPosition(w - 300, 0);

        stage.addActor(table);

    }

    @Override
    public void create () {

    }

    @Override
    public void render(final float delta) {
        Gdx.gl.glClearColor(0, 0, 0.0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();

        game.batch.begin();

        game.batch.end();

        stage.draw();
    }

    @Override
    public boolean keyDown(final int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(final int keycode) {
        if(keycode == Keys.LEFT)
            camera.translate(-32,0);
        if(keycode == Keys.RIGHT)
            camera.translate(32,0);
        if(keycode == Keys.UP)
            camera.translate(0,32);
        if(keycode == Keys.DOWN)
            camera.translate(0,-32);

        return false;
    }

    @Override
    public boolean keyTyped(final char character) {

        return false;
    }

    @Override
    public boolean touchDown(final int screenX, final int screenY, final int pointer, final int button) {
        return false;
    }

    @Override
    public boolean touchUp(final int screenX, final int screenY, final int pointer, final int button) {
        return false;
    }

    @Override
    public boolean touchDragged(final int screenX, final int screenY, final int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(final int screenX, final int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(final int amount) {
        return false;
    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }
}
