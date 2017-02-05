package com.evansitzes.game.buildings;

import com.evansitzes.game.CityBuildingGame;
import com.evansitzes.game.crops.Ground;
import com.evansitzes.game.crops.SeededCarrot;
import com.evansitzes.game.environment.TilesMap;
import com.evansitzes.game.helpers.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by evan on 2/4/17.
 */
public class Farmhouse extends EmployableBuilding {

    private static final int MAX_DISTANCE_FOR_FIELDS = 3; // as long as buildings are square this has to match the farm size
    private static int TILE_SIZE;

    public List<Point> unworkedLand;
    public List<Point> workedLand;
    public List<Ground> tillableLand;
    public List<SeededCarrot> seededLand;

    public Farmhouse(final CityBuildingGame game, final int tileSize, final String type, final int TILE_SIZE) {
        super(game, tileSize, type);
        this.TILE_SIZE = TILE_SIZE;
        unworkedLand = new ArrayList<Point>();
        workedLand = new ArrayList<Point>();
        tillableLand = new ArrayList<Ground>();
        seededLand = new ArrayList<SeededCarrot>();

        maxSpritesCapacity = 1;
    }

    public Point getRandomUnworkedLand() {

        if (unworkedLand.isEmpty()) {
            return null;
        }

        return unworkedLand.get(new Random().nextInt(unworkedLand.size()));
    }

    public void calculateUnworkedLand(final TilesMap tilesMap) {

        if (!isConnectedToRoad) {
            return;
        }

        // Currently this logic requires that the max field distance be the same as the size of the farmhouse
        for (int i = -MAX_DISTANCE_FOR_FIELDS; i < MAX_DISTANCE_FOR_FIELDS + tileSize; i++) {
            for (int j = -MAX_DISTANCE_FOR_FIELDS; j < MAX_DISTANCE_FOR_FIELDS + tileSize; j++) {
                if (!isTileOccupied(x + i * TILE_SIZE, y + j * TILE_SIZE, tilesMap)) {
                    final Point point = new Point();
                    point.x = x + i * TILE_SIZE;
                    point.y = y + j * TILE_SIZE;
                    point.tileX = point.x / TILE_SIZE;
                    point.tileY = point.y / TILE_SIZE;

                    unworkedLand.add(point);
                    final Ground ground = new Ground(game);
                    ground.x = x + i * TILE_SIZE;
                    ground.y = y + j * TILE_SIZE;

                    tillableLand.add(ground);
                }

            }

        }

    }

    public void beginCultivatingLand(final int x, final int y) {
        for (final Point point : unworkedLand) {
            if (point.x == x && point.y == y) {
                unworkedLand.remove(point);
                workedLand.add(point);

                final SeededCarrot carrot = new SeededCarrot(game);
                carrot.x = x;
                carrot.y = y;
                seededLand.add(carrot);

                return;
            }
        }
    }

    public void drawFields() {
        for (final Ground ground : tillableLand) {
            ground.draw();
        }
        for (final SeededCarrot carrot : seededLand) {
            carrot.draw();
        }
    }

    private static boolean isTileOccupied(final int xPixels, final int yPixels, final TilesMap tilesMap) {

        // Check for edge of the map
        if (xPixels < 0 || yPixels < 0 || xPixels / TILE_SIZE >= TILE_SIZE || yPixels / TILE_SIZE >= TILE_SIZE) {
            return false;
        }

        final Building nextBuilding = tilesMap.getTile(xPixels / TILE_SIZE, yPixels / TILE_SIZE).getBuilding();
        if (nextBuilding == null) {
            return false;
        }
//        return tilesMap.getTile(xPixels / TILE_SIZE, yPixels / TILE_SIZE).getBuilding().name.equals("road");
        return true;
    }

}
