package com.evansitzes.game.people;

import com.evansitzes.game.environment.EnhancedTile;
import com.evansitzes.game.environment.TilesMap;
import com.evansitzes.game.helpers.DirectionTile;
import com.evansitzes.game.people.sprites.Person.Facing;

import java.util.*;

/**
- Finds the shortest path between two points on the map using a breadth first search.
- The first path of finding the target (x, y) is returned
- Check tiles are stored in a list and each tile knows what 'parent' tile called it
- Tiles cannot be checked twice
 */
public class SpriteShortestPathFinder {

    public static final Stack<Facing> getShortestPathToBuilding(final TilesMap tilesMap, final int currentX, final int currentY, final List<EnhancedTile> adjacentRoads) {
        Stack<Facing> shortestPath = new Stack<Facing>();

        for (final EnhancedTile road : adjacentRoads) {
            final Stack<Facing> newPath = getShortestPathToTile(tilesMap, currentX, currentY, road.getX(), road.getY());

            // TODO what about when the sprite is already there?
            if (newPath.size() < shortestPath.size() || shortestPath.isEmpty()) {
                shortestPath = newPath;
            }
        }

        return shortestPath;
    }

    public static final Stack<Facing> getShortestPathToTile(final TilesMap tilesMap, final int currentX, final int currentY, final int targetX, final int targetY) {
        final List<DirectionTile> checkedTiles = new ArrayList<DirectionTile>();
        final Queue<DirectionTile> tilesToCheck = new LinkedList<DirectionTile>();
        tilesToCheck.add(new DirectionTile(null, null, currentX, currentY));
        final DirectionTile targetTile;

        while (true) {

            // No path
            if (tilesToCheck.isEmpty()) {
                //TODO no path to building or it is too far away. throw error/handle sprite
                return null;
            }

            //TODO max number of paths to check

            // Take current tile from queue and add to to-check queue if it has not been checked
            final DirectionTile currentTile = tilesToCheck.poll();
            checkedTiles.add(currentTile);

            // Found destination
            if (currentTile.x == targetX && currentTile.y == targetY) {
                targetTile = currentTile;
                break;
            }

            // Check all four directions
            final EnhancedTile leftTile = tilesMap.getTile(currentTile.x - 1, currentTile.y);
            if (leftTile != null && !leftTile.isHasBeenChecked() && leftTile.isOccupied() && leftTile.getBuilding().name.equals("road")) {
                leftTile.setHasBeenChecked(true);
                tilesToCheck.add(new DirectionTile(currentTile, Facing.LEFT, leftTile.getX(), leftTile.getY()));
            }

            final EnhancedTile rightTile = tilesMap.getTile(currentTile.x + 1, currentTile.y);
            if (rightTile != null && !rightTile.isHasBeenChecked() && rightTile.isOccupied() && rightTile.getBuilding().name.equals("road")) {
                rightTile.setHasBeenChecked(true);
                tilesToCheck.add(new DirectionTile(currentTile, Facing.RIGHT, rightTile.getX(), rightTile.getY()));
            }

            final EnhancedTile upTile = tilesMap.getTile(currentTile.x, currentTile.y + 1);
            if (upTile != null && !upTile.isHasBeenChecked() && upTile.isOccupied() && upTile.getBuilding().name.equals("road")) {
                upTile.setHasBeenChecked(true);
                tilesToCheck.add(new DirectionTile(currentTile, Facing.UP, upTile.getX(), upTile.getY()));
            }

            final EnhancedTile downTile = tilesMap.getTile(currentTile.x, currentTile.y - 1);
            if (downTile != null && !downTile.isHasBeenChecked() && downTile.isOccupied() && downTile.getBuilding().name.equals("road")) {
                downTile.setHasBeenChecked(true);
                tilesToCheck.add(new DirectionTile(currentTile, Facing.DOWN, downTile.getX(), downTile.getY()));
            }

        }

        tilesMap.resetAllTileChecks();
        final Stack<Facing> directions = new Stack<Facing>();
        directions.add(targetTile.getDirection());
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
