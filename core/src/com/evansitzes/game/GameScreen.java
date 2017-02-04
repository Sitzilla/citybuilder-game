package com.evansitzes.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.Cursor.SystemCursor;
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
import com.evansitzes.game.Textures.Sidebar;
import com.evansitzes.game.buildings.Building;
import com.evansitzes.game.buildings.EmployableBuilding;
import com.evansitzes.game.buildings.House;
import com.evansitzes.game.buildings.Road;
import com.evansitzes.game.environment.EnhancedTile;
import com.evansitzes.game.environment.Level;
import com.evansitzes.game.environment.TilesMap;
import com.evansitzes.game.helpers.BuildingHelper;
import com.evansitzes.game.helpers.Direction;
import com.evansitzes.game.helpers.Point;
import com.evansitzes.game.helpers.TextHelper;
import com.evansitzes.game.people.SpriteGenerator;
import com.evansitzes.game.people.SpriteHelper;
import com.evansitzes.game.people.SpriteMovementHandler;
import com.evansitzes.game.people.SpriteShortestPathFinder;
import com.evansitzes.game.people.sprites.Person;
import com.evansitzes.game.state.Definition;
import com.evansitzes.game.state.SaveStateHelper;
import com.evansitzes.game.state.handlers.EmploymentStateHandler;
import com.evansitzes.game.state.handlers.PopulationStateHandler;
import com.evansitzes.game.state.handlers.SpriteStateHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.evansitzes.game.helpers.BuildingHelper.createBlankBuilding;
import static com.evansitzes.game.helpers.BuildingHelper.createGenericBuilding;
import static com.evansitzes.game.helpers.DraggingHelper.calculateDraggingPoints;
import static com.evansitzes.game.helpers.DraggingHelper.createDraggedRoads;
import static com.evansitzes.game.helpers.PointHelper.getCornerTileFromMiddleArea;
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

    private TextureRegionDrawable bulldozingImage;
    private Pixmap bulldozingPixmap;
    private float buildingX;
    private float buildingY;

    private NinePatch border;
//    private String populationLabel;
    private Label populationLabel;

    private Skin skin;
    private Stage stage;
    Label fpsLabel;

    private boolean buildingSelected;
    private boolean bulldozingEnabled;
    private Building selectedBuilding;
//    private String currentBuildingName;
//    private String getCurrentBuildingPrettyName;
//    private int currentImageTilesize;
//    private TextureRegionDrawable selectedBuildingImage;

    private final ArrayList<Building> buildings;
    private final ArrayList<House> houses;
    private final ArrayList<Road> roads;
    private final ArrayList<EmployableBuilding> employableBuildings;
    private final Map<String, Definition> objectDefinitions;

    private final CityBuildingGame game;
    private final GameflowController gameflowController;

    private final SpriteStateHandler spriteStateHandler;
    private final SpriteMovementHandler spriteMovementHandler;
    private final PopulationStateHandler populationStateHandler;
    private final EmploymentStateHandler employmentStateHandler;
    private final BuildingHelper buildingHelper;

    private List<Point> draggedPoints = new ArrayList<Point>();
    private List<Road> draggedRoads = new ArrayList<Road>();
    private Point startDraggingPoint = new Point();
    private Point currentDraggingPoint = new Point();
    private boolean isDragging;

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
        bulldozingPixmap = new Pixmap(Gdx.files.internal("sidebar/bulldozer.png"));
//        bulldozingPixmap.dispose();

        // Load State
        objectDefinitions = SaveStateHelper.loadObjectDefinitions();
        buildingHelper = new BuildingHelper(objectDefinitions);
        buildings = SaveStateHelper.loadBuildingsState(game, objectDefinitions);
        tilesMap = SaveStateHelper.loadTilesState(level.mapWidth, level.mapHeight, level.tileHeight, level.tileWidth, buildings);
        houses = new ArrayList<House>();
        roads = new ArrayList<Road>();
        employableBuildings = new ArrayList<EmployableBuilding>();
        selectedBuilding = createBlankBuilding(game);
        initializeBuildingArrays();

        this.tiledMapRenderer = new OrthogonalTiledMapRenderer(level.map);
        tiledMapRenderer.setView(camera);

        border = new NinePatch(Textures.Colors.RED, 0, 0, 0, 0);

        sidebar = Sidebar.BACKGROUND;
        sidebarDrawable = new TextureRegionDrawable(sidebar);

        // TODO externalize this
        final TextButton saveButton = new TextButton("Save", skin);
        final Button houseButton = new Button(new Image(Sidebar.HOUSE), skin);
        final Button guardHouseButton = new Button(new Image(Sidebar.GUARD_HOUSE), skin);
        final Button roadButton = new Button(new Image(Textures.Road.VERTICLE_ROAD), skin);
        final Button bulldozeButton = new Button(new Image(Sidebar.BULLDOZER), skin);

        houseButton.addListener(new ClickListener() {
            @Override
            public void clicked(final InputEvent event, final float x, final float y) {
                selectedBuilding = createGenericBuilding(game, "house");
                buildingSelected = true;
                bulldozingEnabled = false;
            }
        });

        guardHouseButton.addListener(new ClickListener() {
            @Override
            public void clicked(final InputEvent event, final float x, final float y) {
                selectedBuilding = createGenericBuilding(game, "guard_house");
                buildingSelected = true;
                bulldozingEnabled = false;
            }
        });

        roadButton.addListener(new ClickListener() {
            @Override
            public void clicked(final InputEvent event, final float x, final float y) {
                selectedBuilding = createGenericBuilding(game, "road");
                buildingSelected = true;
                bulldozingEnabled = false;
            }
        });

        saveButton.addListener(new ClickListener() {
            @Override
            public void clicked(final InputEvent event, final float x, final float y) {
                System.out.println("Saving!");
                SaveStateHelper.saveBuildingsState(buildings);
                SaveStateHelper.saveTilesState(tilesMap);
            }
        });

        bulldozeButton.addListener(new ClickListener() {
            @Override
            public void clicked(final InputEvent event, final float x, final float y) {
                System.out.println("Deleting!");
                selectedBuilding = createBlankBuilding(game);
                bulldozingImage = new TextureRegionDrawable((Sidebar.BULLDOZER));
                bulldozingEnabled = true;
                buildingSelected = false;
            }
        });

        final Table entireScreenTable = new Table();
        final Table sidebarTable = new Table();
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
//        populationLabel = "0";
        populationLabel = new Label("0", skin);
        populationLabel.setPosition(Gdx.graphics.getWidth() - 625, Gdx.graphics.getHeight() - 38);
        stage.addActor(TextHelper.createText("Population: ", Gdx.graphics.getWidth() - 700, Gdx.graphics.getHeight() - 35));
        stage.addActor(populationLabel);
//        stage.addActor(TextHelper.createText("100", Gdx.graphics.getWidth() - 620, Gdx.graphics.getHeight() - 35));

        final InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(this);
        Gdx.input.setInputProcessor(multiplexer);


        // Create sprites
        spriteStateHandler = new SpriteStateHandler(game, employableBuildings, this);
        spriteMovementHandler = new SpriteMovementHandler(game, TILE_SIZE, tilesMap);

        populationStateHandler = new PopulationStateHandler(houses);
        employmentStateHandler = new EmploymentStateHandler(employableBuildings);
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

        // Dragging rendering logic
        if (isDragging && !bulldozingEnabled) {
            currentDraggingPoint = getCornerTileFromMiddleArea((int) getMousePositionInGameWorld().x, (int) getMousePositionInGameWorld().y, selectedBuilding.tileSize);
            draggedPoints = calculateDraggingPoints(startDraggingPoint, currentDraggingPoint, TILE_SIZE);
            draggedRoads = createDraggedRoads(game, draggedPoints);

            for (final Road road : draggedRoads) {
                road.draw();
            }
        }

        // Draw selected building around mouse
        if (buildingSelected) {
            final Point currentTile = getCornerTileFromMiddleArea((int) getMousePositionInGameWorld().x, (int) getMousePositionInGameWorld().y, selectedBuilding.tileSize);

            if (currentTile.x > 0 && currentTile.y > 0) {
                drawTileBorder(currentTile.x, currentTile.y, selectedBuilding.tileSize);
                selectedBuilding.image.draw(game.batch,
                        currentTile.x,
                        currentTile.y,
                        level.tileWidth * selectedBuilding.tileSize,
                        level.tileHeight * selectedBuilding.tileSize);
            }
        }

        if (bulldozingEnabled) {
            Gdx.graphics.setCursor(Gdx.graphics.newCursor(bulldozingPixmap, 0, 0));
//             TODO refactor
//             - global variable modification
//            getCornerTileFromMiddleArea(currentMouseX, currentMouseY, 1);
            final Point currentTile = getCornerTileFromMiddleArea((int) getMousePositionInGameWorld().x, (int) getMousePositionInGameWorld().y, 1);
//
//            bulldozingImage.draw(game.batch,
//                    currentTile.x,
//                    currentTile.y,
//                    15,
//                    15);

            if (isDragging) {
                try {
                    if (isClickedSquareOccupied(currentTile)) {
                        deleteBuilding(currentTile);
                    }
                } catch (final NullPointerException e) {
                    System.out.println("Null pointer exception trying to check if square is occupied.");
                }
            }
        }


        for (final Building building : buildings) {
            building.draw();
        }

        // Handle Sprites
        spriteStateHandler.handleSpritesStates(delta);
        spriteMovementHandler.handlePatrollingSprites(delta, spriteStateHandler.getPatrollingPersons());
        spriteMovementHandler.handleReturningHomeSprites(delta, spriteStateHandler.getReturningHomePersons());

        // Handle Population
        populationStateHandler.handlePopulationState(delta);
        populationLabel.setText(String.valueOf(populationStateHandler.totalPopulation));
//        stage.addActor(TextHelper.createText(String.valueOf(populationStateHandler.totalPopulation), Gdx.graphics.getWidth() - 620, Gdx.graphics.getHeight() - 35));

        // Handle Employment
        employmentStateHandler.handleEmploymentState(delta, populationStateHandler.totalPopulation);

        game.batch.end();

        stage.act(delta);
        stage.draw();
//        font.draw(game.batch, "Population:", 400, 400);

    }

    @Override
    public boolean keyDown(final int keycode) {

        // Generate new sprite
        if (keycode == Keys.ENTER) {
//            System.out.println("Generating new sprite");
//            spriteMovementHandler.addSpriteToList(SpriteGenerator.generatePerson(game, this, TILE_SIZE, tilesMap, "basic_person", 385, 500));
            spriteStateHandler.addSpriteToReturningHomeList(spriteStateHandler.getPatrollingPersons().get(0));
            sendSpriteHome(spriteStateHandler.getReturningHomePersons().get(0));
        }

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
                final Point currentTile = getCornerTileFromMiddleArea((int) getMousePositionInGameWorld().x, (int) getMousePositionInGameWorld().y, 1);

                // Out of bounds on the right (on the sidebar)
                //TODO magic numbers
                if (currentTile.x / TILE_SIZE > 31 || currentTile.y / TILE_SIZE > 31) {
                    buildingSelected = false;
                    bulldozingEnabled = false;
                    return false;
                }

                if (isClickedSquareOccupied(currentTile)) {
                    System.out.println("Show Dialog");

                    final BasicInformationPopup popup = new BasicInformationPopup(getClickedTile(currentTile).getBuilding(), skin);
                    stage.addActor(popup);
                }
            }

            buildingSelected = false;
            bulldozingEnabled = false;
            Gdx.graphics.setSystemCursor(SystemCursor.Arrow);
        }

        if (button == Buttons.LEFT) {

            // Drag and Build logic
            if (buildingSelected && selectedBuilding.name.equals("road")) {
                isDragging = true;
                startDraggingPoint = getCornerTileFromMiddleArea((int) getMousePositionInGameWorld().x, (int) getMousePositionInGameWorld().y, selectedBuilding.tileSize);
                startDraggingPoint.tileX = startDraggingPoint.x / TILE_SIZE;
                startDraggingPoint.tileY = startDraggingPoint.y / TILE_SIZE;
                System.out.println(startDraggingPoint);
                return false;
            }

            if (bulldozingEnabled) {
                isDragging = true;
                final Point currentTile = getCornerTileFromMiddleArea((int) getMousePositionInGameWorld().x, (int) getMousePositionInGameWorld().y, 1);

                if (isClickedSquareOccupied(currentTile)) {
                    deleteBuilding(currentTile);
                }

                return false;
            }

            // Place normal building
            final Point currentTile = getCornerTileFromMiddleArea((int) getMousePositionInGameWorld().x, (int) getMousePositionInGameWorld().y, selectedBuilding.tileSize);
            if (buildingSelected && !isPlacementAreaOccupied(currentTile)) {
                final Definition buildingDefinition = objectDefinitions.get(selectedBuilding.name);

                // TODO refactor this out
                if (selectedBuilding.name.equals("house")) {
                    final House building = new House(game, selectedBuilding.tileSize, buildingDefinition.getType());
                    building.name = buildingDefinition.getName();
                    building.prettyName = buildingDefinition.getPrettyName();
                    building.description = buildingDefinition.getDescription();
                    building.x = currentTile.x;
                    building.y = currentTile.y;
                    buildings.add(building);
                    houses.add(building);
                    setTileBuilding(building, currentTile);
                } else if (selectedBuilding.name.equals("road")) {
                    final Road building = new Road(game, selectedBuilding.tileSize, buildingDefinition.getType());
                    building.name = buildingDefinition.getName();
                    building.prettyName = buildingDefinition.getPrettyName();
                    building.description = buildingDefinition.getDescription();
                    building.x = currentTile.x;
                    building.y = currentTile.y;
                    buildings.add(building);
                    roads.add(building);
                    setTileBuilding(building, currentTile);
                } else if (selectedBuilding.name.equals("guard_house")) {
                    final EmployableBuilding building = new EmployableBuilding(game, selectedBuilding.tileSize, buildingDefinition.getType());
                    building.name = buildingDefinition.getName();
                    building.prettyName = buildingDefinition.getPrettyName();
                    building.description = buildingDefinition.getDescription();
                    building.maxEmployability = buildingDefinition.getMaxEmployees();
                    building.x = currentTile.x;
                    building.y = currentTile.y;
                    buildings.add(building);
                    employableBuildings.add(building);
                    setTileBuilding(building, currentTile);
                }
////                spriteStateHandler.refreshBuildings
//                setTileBuilding(building, currentTile);
//                spriteMovementHandler.setBuildings(buildings);
                setAreDevelopedTiles(true, currentTile);
            }
        }

        return false;
    }

    @Override
    public boolean touchUp(final int screenX, final int screenY, final int pointer, final int button) {

        if (bulldozingEnabled && isDragging) {
            isDragging = false;
            return false;
        }

        if (isDragging) {
            final Definition buildingDefinition = objectDefinitions.get("road");

            for (final Road road : draggedRoads) {
                final Point currentTile = new Point();
                currentTile.x = road.x;
                currentTile.y = road.y;

                if (isPlacementAreaOccupied(currentTile)) {
                    continue;
                }

                road.name = buildingDefinition.getName();
                road.prettyName = buildingDefinition.getPrettyName();
                road.description = buildingDefinition.getDescription();
                buildings.add(road);
                roads.add(road);
                setTileBuilding(road, currentTile);
                setAreDevelopedTiles(true, currentTile);

            }

            draggedRoads.clear();
            draggedPoints.clear();
            isDragging = false;
        }

        return false;
    }

    @Override
    public boolean touchDragged(final int screenX, final int screenY, final int pointer) {


        return false;
    }

    @Override
    public boolean mouseMoved(final int screenX, final int screenY) {
//         Not currently used... can use later when wanting to tie an image to the mouse
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

    public void createNewSprite(final String name, final int x, final int y, final EmployableBuilding building) {
        System.out.println("Generating new sprite");

        final Direction direction = SpriteHelper.getRandomValidDirection(x, y, building.tileSize, null, TILE_SIZE, tilesMap);

        System.out.println(direction);
        if (direction == null || direction.facingDirection == null) {
            System.out.println("Building isnt connected to a road!!");
            return;
        }

        final Person newPerson = SpriteGenerator.generatePerson(game,
                this,
                building,
                TILE_SIZE,
                tilesMap,
                name,
                getNextXCornerTileFromDirection(x, TILE_SIZE, building.tileSize, direction.directionIndex, direction.facingDirection),
                getNextYCornerTileFromDirection(y, TILE_SIZE, building.tileSize, direction.directionIndex, direction.facingDirection));

        if (newPerson == null) {
            return;
        }

        spriteStateHandler.addSpriteToList(newPerson);
    }

    public void sendSpriteHome(final Person patrolPerson) {
        final Building homeBuilding = patrolPerson.homeBuilding;
        final List<EnhancedTile> adjacentRoads = SpriteHelper.getBuildingsAdjacentRoads(homeBuilding.x, homeBuilding.y, homeBuilding.tileSize, TILE_SIZE, tilesMap);

        //TODO check for null/empty stack
        patrolPerson.pathHome = SpriteShortestPathFinder.getShortestPathToBuilding(tilesMap, patrolPerson.currentTileX / TILE_SIZE, patrolPerson.currentTileY / TILE_SIZE, adjacentRoads);
        patrolPerson.justBeganReturningHome = true;
    }

    private void deleteBuilding(final Point currentTile) {
        for (final Building building : buildings) {
            if (building.overhangs(currentTile.x, currentTile.y)) {
                buildings.remove(building);
                if (building.name.equals("house")) {
                    houses.remove(building);
                }
                if (building.name.equals("road")) {
                    roads.remove(building);
                }
                if (building.name.equals("guard_house")) {
                    employableBuildings.remove(building);
                }

//                        spriteMovementHandler.setBuildings(buildings);
                //TODO tile size
                currentTile.x = building.x;
                currentTile.y = building.y;
                selectedBuilding.tileSize = building.tileSize;
                setTileBuilding(null, currentTile);
                setAreDevelopedTiles(false, currentTile);
                break;
            }
        }
    }

    private Vector3 getMousePositionInGameWorld() {
        return camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
    }

    private void setAreDevelopedTiles(final boolean isOccupied, final Point currentTile) {
        for (int i = 0; i < selectedBuilding.tileSize; i++) {
            for (int j = 0; j < selectedBuilding.tileSize; j++) {
                tilesMap.getTile(currentTile.x / TILE_SIZE + i, currentTile.y / TILE_SIZE + j).setOccupied(isOccupied);
            }
        }
    }

    private void setTileBuilding(final Building building, final Point currentTile) {
        for (int i = 0; i < selectedBuilding.tileSize; i++) {
            for (int j = 0; j < selectedBuilding.tileSize; j++) {
                tilesMap.getTile(currentTile.x / TILE_SIZE + i, currentTile.y / TILE_SIZE + j).setBuilding(building);
            }
        }
    }

    private EnhancedTile getClickedTile(final Point currentTile) {
        return tilesMap.getTile(currentTile.x / TILE_SIZE, currentTile.y / TILE_SIZE);
    }

    private boolean isClickedSquareOccupied(final Point currentTile) throws NullPointerException {
        return tilesMap.getTile(currentTile.x / TILE_SIZE, currentTile.y / TILE_SIZE).isOccupied();
    }

    private boolean isPlacementAreaOccupied(final Point currentTile) {
        for (int i = 0; i < selectedBuilding.tileSize; i++) {
            for (int j = 0; j < selectedBuilding.tileSize; j++) {
                if (tilesMap.getTile(currentTile.x / TILE_SIZE + i, currentTile.y / TILE_SIZE + j).isOccupied()) {
                    return true;
                }
            }
        }

        return false;
    }

    private void initializeBuildingArrays() {
        // Split buildings into building types
        for (final Building building : buildings) {
            if (building instanceof House) {
                houses.add((House) building);
                continue;
            }
            if (building instanceof Road) {
                roads.add((Road) building);
                continue;
            }
            if (building instanceof EmployableBuilding) {
                employableBuildings.add((EmployableBuilding) building);
                continue;
            }
        }
    }

    private void drawTileBorder(final int bottomLeftCornerXInPixels, final int bottomLeftCornerYInPixels, final int numberOfTiles) {
        border.draw(game.batch, bottomLeftCornerXInPixels - BORDER_WIDTH, bottomLeftCornerYInPixels - BORDER_WIDTH, TILE_SIZE * numberOfTiles + BORDER_WIDTH * 2, BORDER_WIDTH);
        border.draw(game.batch, bottomLeftCornerXInPixels - BORDER_WIDTH, bottomLeftCornerYInPixels - BORDER_WIDTH, BORDER_WIDTH, TILE_SIZE * numberOfTiles + BORDER_WIDTH * 2);
        border.draw(game.batch, bottomLeftCornerXInPixels, bottomLeftCornerYInPixels + TILE_SIZE * numberOfTiles, TILE_SIZE * numberOfTiles + BORDER_WIDTH, BORDER_WIDTH);
        border.draw(game.batch, bottomLeftCornerXInPixels + TILE_SIZE * numberOfTiles, bottomLeftCornerYInPixels, BORDER_WIDTH,  TILE_SIZE * numberOfTiles + BORDER_WIDTH);
    }


}
