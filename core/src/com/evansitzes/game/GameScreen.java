package com.evansitzes.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;


/**
 * Created by evan on 12/13/16.
 */
public class GameScreen extends ApplicationAdapter implements Screen, InputProcessor {

    private static int BORDER_WIDTH = 10;
    private static int TILE_SIZE;

    private Texture img;
    private TiledMap tiledMap;
    private OrthographicCamera camera;
    private TiledMapRenderer tiledMapRenderer;
    private Level level;

    private TextureRegion sidebar;
    private TextureRegionDrawable sidebarDrawable;

    private TextureRegionDrawable selectedBuilding;
    private float buildingX;
    private float buildingY;

    private NinePatch border;

    private Skin skin;
    private Stage stage;
    Label fpsLabel;

    private boolean buildingSelected;
    private int currentMouseX;
    private int currentMouseY;
    private int currentTileX;
    private int currentTileY;

    private final CityBuildingGame game;
    private final GameflowController gameflowController;

    public GameScreen(final CityBuildingGame game, final GameflowController gameflowController) {
        this.game = game;
        this.gameflowController = gameflowController;

        stage = new Stage();

        skin = new Skin(Gdx.files.internal("skins/uiskin.json"));

        final float w = Gdx.graphics.getWidth();
        final float h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, w / Configuration.WIDTH_MODIFIER, h / Configuration.HEIGHT_MODIFIER);
        camera.update();
//        tiledMap = new TmxMapLoader().load("maps/basic-level1.tmx");
        this.level = TmxLevelLoader.load(Vector2.Zero, game, this, "test-map");
        TILE_SIZE = (int) (level.tileHeight * Configuration.WIDTH_MODIFIER);

        this.tiledMapRenderer = new OrthogonalTiledMapRenderer(level.map);
        tiledMapRenderer.setView(camera);

        border = new NinePatch(Textures.Colors.RED, 0, 0, 0, 0);

        sidebar = Textures.Sidebar.SIDEBAR;
        sidebarDrawable = new TextureRegionDrawable(sidebar);
        selectedBuilding = new TextureRegionDrawable((Textures.Sidebar.HOUSE));

        Button imgButton = new Button(new Image(Textures.Sidebar.HOUSE), skin);
//        imgButton.setWidth(500);
//        imgButton.setHeight(500);
        imgButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                buildingSelected = true;
            }
        });

        final Label nameLabel = new Label("Name:", skin);
        final TextField nameText = new TextField("", skin);
        final Label addressLabel = new Label("Address:", skin);
        final TextField addressText = new TextField("", skin);

        final Table table = new Table();
//        table.setFillParent(true);
        table.setBackground(sidebarDrawable);

        table.add(imgButton);

        table.setDebug(true); // turn on all debug lines (table, cell, and widget)
        table.setHeight(h);
        table.setWidth(300);
        table.setPosition(w - 300, 0);

        stage.addActor(table);

        final InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(this);
        Gdx.input.setInputProcessor(multiplexer);    }

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

        // TODO refactor
        // - global variable modification
        setCornerTileFromMiddleArea(currentMouseX, currentMouseY, 6);
        drawTileBorder(currentTileX, currentTileY, 6);

        if (buildingSelected) {
            selectedBuilding.draw(game.batch,
                                    buildingX,
                                    buildingY,
                                    level.tileWidth * Configuration.WIDTH_MODIFIER * 2,
                                    level.tileHeight * Configuration.HEIGHT_MODIFIER * 2);
        }

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

        if (button == Buttons.RIGHT) {
            buildingSelected = false;
        }

        if (button == Buttons.LEFT) {
            System.out.println(screenX);
            System.out.println(Gdx.graphics.getHeight() - screenY);

        }

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
            buildingX = screenX - 50;
            buildingY = Gdx.graphics.getHeight() - screenY - 50;

//            if (buildingSelected) {
                currentMouseX = screenX;
                currentMouseY = Gdx.graphics.getHeight() - screenY;
//            }

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

    private void setCornerTileFromMiddleArea(final int screenX, final int screenY, int numberOfTiles) {

        // tile group has a definite tile as the center
        if (numberOfTiles % 2 != 0) {
            currentTileX = screenX / TILE_SIZE * TILE_SIZE - TILE_SIZE * (numberOfTiles / 2);
            currentTileY = screenY / TILE_SIZE * TILE_SIZE - TILE_SIZE * (numberOfTiles / 2);
            return;
        }

        final int midPointX = getClosestCorner(screenX);
        final int midPointY = getClosestCorner(screenY);

        final int lowerBoundX = midPointX - 20;
        final int upperBoundX = lowerBoundX + 40;

        final int lowerBoundY = midPointY - 20;
        final int upperBoundY = lowerBoundY + 40;

        // In lower-left quadrant
        if (screenX < midPointX && screenX > lowerBoundX &&
                screenY < midPointY && screenY > lowerBoundY) {

            currentTileX = screenX / TILE_SIZE * TILE_SIZE - TILE_SIZE * ((numberOfTiles / 2) - 1);
            currentTileY = screenY / TILE_SIZE * TILE_SIZE - TILE_SIZE * ((numberOfTiles / 2) - 1);
            return;
        }

        // In lower-right quadrant
        if (screenX > midPointX && screenX < upperBoundX &&
                screenY < midPointY && screenY > lowerBoundY) {

            currentTileX = screenX / TILE_SIZE * TILE_SIZE - TILE_SIZE * (numberOfTiles / 2);
            currentTileY = screenY / TILE_SIZE * TILE_SIZE - TILE_SIZE * ((numberOfTiles / 2) - 1);
            return;
        }

        // In upper-left quadrant
        if (screenX < midPointX && screenX > lowerBoundX &&
                screenY > midPointY && screenY < upperBoundY) {
            currentTileX = screenX / TILE_SIZE * TILE_SIZE - TILE_SIZE * ((numberOfTiles / 2) - 1);
            currentTileY = screenY / TILE_SIZE * TILE_SIZE - TILE_SIZE * (numberOfTiles / 2);
            return;
        }

        // In upper-right quadrant
        if (screenX > midPointX && screenX < upperBoundX &&
                screenY > midPointY && screenY < upperBoundY) {
            currentTileX = screenX / TILE_SIZE * TILE_SIZE - TILE_SIZE * (numberOfTiles / 2);
            currentTileY = screenY / TILE_SIZE * TILE_SIZE - TILE_SIZE * (numberOfTiles / 2);
            return;
        }

    }

    private static int getClosestCorner(final int point) {
        final int lowerBound = point / TILE_SIZE * TILE_SIZE;
        final int upperBound = lowerBound + TILE_SIZE;

        final int lowerDifference = Math.abs(lowerBound - point);
        final int upperDifference = Math.abs(upperBound - point);

        if (lowerDifference > upperDifference) {
            return upperBound;
        }

        return lowerBound;
    }

    private void drawTileBorder(final int bottomLeftCornerXInPixels, final int bottomLeftCornerYInPixels, final int numberOfTiles) {
        border.draw(game.batch, bottomLeftCornerXInPixels - BORDER_WIDTH, bottomLeftCornerYInPixels - BORDER_WIDTH, TILE_SIZE * numberOfTiles + BORDER_WIDTH * 2, BORDER_WIDTH);
        border.draw(game.batch, bottomLeftCornerXInPixels - BORDER_WIDTH, bottomLeftCornerYInPixels - BORDER_WIDTH, BORDER_WIDTH, TILE_SIZE * numberOfTiles + BORDER_WIDTH * 2);
        border.draw(game.batch, bottomLeftCornerXInPixels, bottomLeftCornerYInPixels + TILE_SIZE * numberOfTiles, TILE_SIZE * numberOfTiles + BORDER_WIDTH, BORDER_WIDTH);
        border.draw(game.batch, bottomLeftCornerXInPixels + TILE_SIZE * numberOfTiles, bottomLeftCornerYInPixels, BORDER_WIDTH,  TILE_SIZE * numberOfTiles + BORDER_WIDTH);
    }
}
