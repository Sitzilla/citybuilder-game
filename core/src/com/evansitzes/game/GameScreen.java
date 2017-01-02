package com.evansitzes.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.evansitzes.game.buildings.Building;
import com.evansitzes.game.environment.Level;
import com.evansitzes.game.environment.TilesMap;
import com.evansitzes.game.helpers.Direction;
import com.evansitzes.game.helpers.TextHelper;
import com.evansitzes.game.people.*;
import com.evansitzes.game.state.StateHelper;

import java.util.ArrayList;

import static com.evansitzes.game.people.SpriteHelper.getNextXCornerTileFromDirection;
import static com.evansitzes.game.people.SpriteHelper.getNextYCornerTileFromDirection;


/**
 * Created by evan on 12/13/16.
 */
public class GameScreen extends ApplicationAdapter implements Screen, InputProcessor {

    private static int BORDER_WIDTH = 1;
    private static int TILE_SIZE;

    private Texture img;
    private TiledMap tiledMap;
    private OrthographicCamera camera;
    private TiledMapRenderer tiledMapRenderer;
    private Level level;
    private final TilesMap tilesMap;

    private TextureRegion sidebar;
    private TextureRegionDrawable sidebarDrawable;
    private TextureRegionDrawable topbarDrawable;

    private TextureRegionDrawable selectedBuildingImage;
    private TextureRegionDrawable bulldozingImage;
//    Pixmap bulldozingPixmap;
    private float buildingX;
    private float buildingY;

    private NinePatch border;

    private Skin skin;
    private Stage stage;
    Label fpsLabel;

    private boolean buildingSelected;
    private boolean bulldozingEnabled;
    //TODO replace with building object maybe?
    private String currentBuildingName;
    private int currentImageTilesize;
//    private int currentMouseX;
//    private int currentMouseY;
    private int currentTileX;
    private int currentTileY;

    private final ArrayList<Building> buildings;
//    private final ArrayList<Tile> tilesMap = new ArrayList<Tile>();
//    private final TreeSet<Integer> developedXTiles = new TreeSet<Integer>();
//    private final TreeSet<Integer> developedYTiles = new TreeSet<Integer>();

    private final CityBuildingGame game;
    private final GameflowController gameflowController;

    private final SpriteStateHandler spriteStateHandler;
    private final SpriteMovementHandler spriteMovementHandler;

    public GameScreen(final CityBuildingGame game, final GameflowController gameflowController) {
        this.game = game;
        this.gameflowController = gameflowController;

        stage = new Stage();

        skin = new Skin(Gdx.files.internal("skins/uiskin.json"));

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.update();
        this.level = TmxLevelLoader.load(Vector2.Zero, game, this, "test-map");
        TILE_SIZE = (int) (level.tileHeight);
//        bulldozingPixmap = new Pixmap(Gdx.files.internal("sidebar/bulldozer.png"));
//        bulldozingPixmap.dispose();

        // Load State
        buildings = StateHelper.loadBuildingsState(game);
        tilesMap = StateHelper.loadTilesState(level.mapWidth, level.mapHeight, level.tileHeight, level.tileWidth, buildings);

        this.tiledMapRenderer = new OrthogonalTiledMapRenderer(level.map);
        tiledMapRenderer.setView(camera);

        border = new NinePatch(Textures.Colors.RED, 0, 0, 0, 0);

        sidebar = Textures.Sidebar.BACKGROUND;
        sidebarDrawable = new TextureRegionDrawable(sidebar);

        // TODO externalize this
        final TextButton saveButton = new TextButton("Save", skin);
        final Button houseButton = new Button(new Image(Textures.Sidebar.HOUSE), skin);
        final Button guardHouseButton = new Button(new Image(Textures.Sidebar.GUARD_HOUSE), skin);
        final Button roadButton = new Button(new Image(Textures.Road.VERTICLE_ROAD), skin);
        final Button bulldozeButton = new Button(new Image(Textures.Sidebar.BULLDOZER), skin);

        houseButton.addListener(new ClickListener() {
            @Override
            public void clicked(final InputEvent event, final float x, final float y) {
                currentImageTilesize = 2;
                currentBuildingName = "house";
                selectedBuildingImage = new TextureRegionDrawable((Textures.Sidebar.HOUSE));
                buildingSelected = true;
                bulldozingEnabled = false;
            }
        });

        guardHouseButton.addListener(new ClickListener() {
            @Override
            public void clicked(final InputEvent event, final float x, final float y) {
                currentImageTilesize = 2;
                currentBuildingName = "guard_house";
                selectedBuildingImage = new TextureRegionDrawable((Textures.Sidebar.GUARD_HOUSE));
                buildingSelected = true;
                bulldozingEnabled = false;
            }
        });

        roadButton.addListener(new ClickListener() {
            @Override
            public void clicked(final InputEvent event, final float x, final float y) {
                currentImageTilesize = 1;
                currentBuildingName = "road";
                selectedBuildingImage = new TextureRegionDrawable((Textures.Road.VERTICLE_ROAD));
                buildingSelected = true;
                bulldozingEnabled = false;
            }
        });

        saveButton.addListener(new ClickListener() {
            @Override
            public void clicked(final InputEvent event, final float x, final float y) {
                System.out.println("Saving!");
                StateHelper.saveBuildingsState(buildings);
                StateHelper.saveTilesState(tilesMap);
            }
        });

        bulldozeButton.addListener(new ClickListener() {
            @Override
            public void clicked(final InputEvent event, final float x, final float y) {
                System.out.println("Deleting!");
                bulldozingImage = new TextureRegionDrawable((Textures.Sidebar.BULLDOZER));
                bulldozingEnabled = true;
                buildingSelected = false;
            }
        });

//        final Label nameLabel = new Label("Name:", skin);
//        final TextField nameText = new TextField("", skin);
//        final Label addressLabel = new Label("Address:", skin);
//        final TextField addressText = new TextField("", skin);

        final Table entireScreenTable = new Table();
        final Table sidebarTable = new Table();
//        table.setFillParent(true);
        entireScreenTable.setBackground(sidebarDrawable);

        sidebarTable.add(houseButton);
        sidebarTable.add(guardHouseButton);
        sidebarTable.row();
        sidebarTable.add(roadButton);
        sidebarTable.add(saveButton);
        sidebarTable.add(bulldozeButton);

        entireScreenTable.setDebug(true); // turn on all debug lines (table, cell, and widget)
        entireScreenTable.setHeight(Gdx.graphics.getHeight());
        entireScreenTable.setWidth(Gdx.graphics.getWidth());

        sidebarTable.setDebug(true); // turn on all debug lines (table, cell, and widget)
        sidebarTable.setHeight(Gdx.graphics.getHeight());
        sidebarTable.setWidth(300);
        sidebarTable.setPosition(Gdx.graphics.getWidth() - 300, 0);

        stage.addActor(entireScreenTable);
        stage.addActor(sidebarTable);

        // Add topbar text
        stage.addActor(TextHelper.createText("Population: ", Gdx.graphics.getWidth() - 700, Gdx.graphics.getHeight() - 35));
        stage.addActor(TextHelper.createText("100", Gdx.graphics.getWidth() - 620, Gdx.graphics.getHeight() - 35));

        final InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(this);
        Gdx.input.setInputProcessor(multiplexer);


        // Create sprites
        spriteStateHandler = new SpriteStateHandler(game, buildings, this);
        spriteMovementHandler = new SpriteMovementHandler(game, TILE_SIZE, tilesMap);
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
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();

//        font.draw(game.batch, "Population:", Gdx.graphics.getWidth() - 400, Gdx.graphics.getHeight() - 10);

        if (buildingSelected) {
            // TODO refactor
            // - global variable modification
            setCornerTileFromMiddleArea((int) getMousePositionInGameWorld().x, (int) getMousePositionInGameWorld().y, currentImageTilesize);
            drawTileBorder(currentTileX, currentTileY, currentImageTilesize);

            selectedBuildingImage.draw(game.batch,
                                    currentTileX,
                                    currentTileY,
                                    level.tileWidth * currentImageTilesize,
                                    level.tileHeight * currentImageTilesize);
        }

        if (bulldozingEnabled) {

//            Gdx.graphics.setCursor(Gdx.graphics.newCursor(bulldozingPixmap, 0, 0));
            // TODO refactor
            // - global variable modification
//            setCornerTileFromMiddleArea(currentMouseX, currentMouseY, 1);
//            bulldozingImage.draw(game.batch,
//                    currentTileX,
//                    currentTileY,
//                    15,
//                    15);
        }


        for (final Building building : buildings) {
            building.draw();
        }

        spriteStateHandler.handleSpritesStates();
        spriteMovementHandler.handleAllSprites(delta);

        game.batch.end();

        stage.act(delta);
        stage.draw();
//        font.draw(game.batch, "Population:", 400, 400);

    }

    @Override
    public boolean keyDown(final int keycode) {

        // Generate new sprite
        if (keycode == Input.Keys.ENTER) {
            System.out.println("Generating new sprite");
            spriteMovementHandler.addSpriteToList(SpriteGenerator.generatePerson(game, this, TILE_SIZE, tilesMap, "basic_person", 385, 500));
        }

        if(keycode == Input.Keys.LEFT)
            camera.translate(-32,0);
        if(keycode == Input.Keys.RIGHT)
            camera.translate(32,0);
        if(keycode == Input.Keys.UP)
            camera.translate(0,32);
        if(keycode == Input.Keys.DOWN)
            camera.translate(0,-32);

        return false;
    }

    @Override
    public boolean keyUp(final int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(final char character) {

        return false;
    }

    @Override
    public boolean touchDown(final int screenX, final int screenY, final int pointer, final int button) {

        if (button == Buttons.RIGHT) {

            // Show information popup
            if (!buildingSelected && !bulldozingEnabled) {
                setCornerTileFromMiddleArea((int) getMousePositionInGameWorld().x, (int) getMousePositionInGameWorld().y, 1);
                if (isClickedSquareOccupied()) {
                    System.out.println("Show Dialog");

                    BasicInformationPopup popup = new BasicInformationPopup("Building Info", skin);
                    stage.addActor(popup);
                }
            }

            buildingSelected = false;
            bulldozingEnabled = false;
//            Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
        }

        if (button == Buttons.LEFT) {
            System.out.println(screenX);
            System.out.println(Gdx.graphics.getHeight() - screenY);

            if (currentTileX < 0 || currentTileY < 0) {
                return false;
            }

            if (buildingSelected && !isPlacementAreaOccupied()) {
                final Building building = new Building(game, currentImageTilesize, currentBuildingName);
                building.x = currentTileX;
                building.y = currentTileY;

                buildings.add(building);
//                spriteStateHandler.refreshBuildings
                setTileBuilding(building);
//                spriteMovementHandler.setBuildings(buildings);
                setAreDevelopedTiles(true);
            }

            setCornerTileFromMiddleArea((int) getMousePositionInGameWorld().x, (int) getMousePositionInGameWorld().y, 1);

            if (bulldozingEnabled && isClickedSquareOccupied()) {

                for (final Building building : buildings) {
                    if (building.overhangs(currentTileX, currentTileY)) {
                        buildings.remove(building);
                        setTileBuilding(null);
//                        spriteMovementHandler.setBuildings(buildings);
                        //TODO tile size
//                        setCornerTileFromMiddleArea((int) getMousePositionInGameWorld().x, (int) getMousePositionInGameWorld().y, building.tileSize);
                        currentImageTilesize = building.tileSize;
                        setAreDevelopedTiles(false);
                        break;
                    }
                }

            }
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

        //TODO magic numbers
        // Not currently used... can use later when wanting to tie an image to the mouse
//            buildingX = screenX - 25 * currentImageTilesize;
//            buildingY = Gdx.graphics.getHeight() - screenY - 25 * currentImageTilesize;

//            currentMouseX = screenX;
//            currentMouseY = Gdx.graphics.getHeight() - screenY;

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

    public void createNewSprite(final String name, final int x, final int y, final int buildingSize) {
        System.out.println("Generating new sprite");

        Direction direction = SpriteHelper.getRandomValidDirection(x, y, buildingSize, null, TILE_SIZE, tilesMap);

        System.out.println(direction);
        if (direction == null || direction.facingDirection == null) {
            System.out.println("Building isnt connected to a road!!");
            return;
        }

        final Person newPerson = SpriteGenerator.generatePerson(game,
                this,
                TILE_SIZE,
                tilesMap,
                name,
                getNextXCornerTileFromDirection(x, TILE_SIZE, buildingSize, direction.directionIndex, direction.facingDirection),
                getNextYCornerTileFromDirection(y, TILE_SIZE, buildingSize, direction.directionIndex, direction.facingDirection));

        if (newPerson == null) {
            return;
        }

        spriteMovementHandler.addSpriteToList(newPerson);
    }

    private Vector3 getMousePositionInGameWorld() {
        return camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
    }

    private void setAreDevelopedTiles(final boolean isOccupied) {
        for (int i = 0; i < currentImageTilesize; i++) {
            for (int j = 0; j < currentImageTilesize; j++) {
                tilesMap.getTile(currentTileX / TILE_SIZE + i, currentTileY / TILE_SIZE + j).setOccupied(isOccupied);
            }
        }
    }

    private void setTileBuilding(final Building building) {
        for (int i = 0; i < currentImageTilesize; i++) {
            for (int j = 0; j < currentImageTilesize; j++) {
                tilesMap.getTile(currentTileX / TILE_SIZE + i, currentTileY / TILE_SIZE + j).setBuilding(building);
            }
        }
    }

    private boolean isClickedSquareOccupied() {
        return tilesMap.getTile(currentTileX / TILE_SIZE, currentTileY / TILE_SIZE).isOccupied();
    }

    private boolean isPlacementAreaOccupied() {
        for (int i = 0; i < currentImageTilesize; i++) {
            for (int j = 0; j < currentImageTilesize; j++) {
                if (tilesMap.getTile(currentTileX / TILE_SIZE + i, currentTileY / TILE_SIZE + j).isOccupied()) {
                    return true;
                }
            }
        }

        return false;
    }

    private void setCornerTileFromMiddleArea(final int screenX, final int screenY, final int numberOfTiles) {

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
