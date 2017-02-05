package com.evansitzes.game.people;

import com.evansitzes.game.environment.EnhancedTile;
import com.evansitzes.game.environment.TilesMap;
import com.evansitzes.game.helpers.DirectionTile;
import com.evansitzes.game.helpers.Point;
import com.evansitzes.game.people.sprites.Person.Facing;

import java.util.*;

/**
 * Returns a path to the closest unworked patch of land
 */
public class SpriteHarvestingMovement {

    public static final Stack<Facing> getShortestPathToLand(final TilesMap tilesMap, final int currentTileX, final int currentTileY, final Point nextPoint) {
        final Stack<Facing> pathToTile = getShortestPathToTile(tilesMap, currentTileX, currentTileY, nextPoint.tileX, nextPoint.tileY);

        // TODO do something about this null check. Should the sprite just die?
        if (pathToTile == null) {
            System.out.println("No path to tile!");
            return new Stack<Facing>();
        }

        return pathToTile;
    }

    public static final Stack<Facing> getShortestPathToTile(final TilesMap tilesMap, final int currentTileX, final int currentTileY, final int targetTileX, final int targetTileY) {
        final List<DirectionTile> checkedTiles = new ArrayList<DirectionTile>();
        final Queue<DirectionTile> tilesToCheck = new LinkedList<DirectionTile>();
        tilesToCheck.add(new DirectionTile(null, null, currentTileX, currentTileY));
        final DirectionTile targetTile;

        while (true) {

            // No path
            if (tilesToCheck.isEmpty()) {
                //TODO no path to building or it is too far away. throw error/handle sprite
                System.out.println("No path to tile!");
                return new Stack<Facing>();
            }

            //TODO max number of paths to check

            // Take current tile from queue and add to to-check queue if it has not been checked
            final DirectionTile currentTile = tilesToCheck.poll();
            checkedTiles.add(currentTile);

            // Found destination
            if (currentTile.x == targetTileX && currentTile.y == targetTileY) {
                targetTile = currentTile;
                break;
            }

            // Check all four directions
            final EnhancedTile leftTile = tilesMap.getTile(currentTile.x - 1, currentTile.y);
            if (leftTile != null && !leftTile.isHasBeenChecked() && (!leftTile.isOccupied() || leftTile.getBuilding().name.equals("road"))) {
                leftTile.setHasBeenChecked(true);
                tilesToCheck.add(new DirectionTile(currentTile, Facing.LEFT, leftTile.getX(), leftTile.getY()));
            }

            final EnhancedTile rightTile = tilesMap.getTile(currentTile.x + 1, currentTile.y);
            if (rightTile != null && !rightTile.isHasBeenChecked() && (!rightTile.isOccupied() || rightTile.getBuilding().name.equals("road"))) {
                rightTile.setHasBeenChecked(true);
                tilesToCheck.add(new DirectionTile(currentTile, Facing.RIGHT, rightTile.getX(), rightTile.getY()));
            }

            final EnhancedTile upTile = tilesMap.getTile(currentTile.x, currentTile.y + 1);
            if (upTile != null && !upTile.isHasBeenChecked() && (!upTile.isOccupied() || upTile.getBuilding().name.equals("road"))) {
                upTile.setHasBeenChecked(true);
                tilesToCheck.add(new DirectionTile(currentTile, Facing.UP, upTile.getX(), upTile.getY()));
            }

            final EnhancedTile downTile = tilesMap.getTile(currentTile.x, currentTile.y - 1);
            if (downTile != null && !downTile.isHasBeenChecked() && (!downTile.isOccupied() || downTile.getBuilding().name.equals("road"))) {
                downTile.setHasBeenChecked(true);
                tilesToCheck.add(new DirectionTile(currentTile, Facing.DOWN, downTile.getX(), downTile.getY()));
            }

        }

        tilesMap.resetAllTileChecks();
        final Stack<Facing> directions = new Stack<Facing>();
//        directions.add(targetTile.getDirection());
        return getPathFromTarget(targetTile, directions);
    }

    private static Stack<Facing> getPathFromTarget(final DirectionTile tile, final Stack<Facing> directions) {

        // Base Case: parent tile
        if (tile.getParentTile() == null) {
            return directions;
        }

        directions.add(tile.getDirection());
        return getPathFromTarget(tile.getParentTile(), directions);
    }
}
