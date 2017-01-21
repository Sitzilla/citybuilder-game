package com.evansitzes.game.helpers;

import com.evansitzes.game.CityBuildingGame;
import com.evansitzes.game.buildings.Road;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by evan on 1/21/17.
 */
public class DraggingHelper {

    public static List<Point> calculateDraggingPoints(final Point startDraggingPoint, final Point currentDraggingPoint, final int TILE_SIZE) {
        final List<Point> draggedPoints = new ArrayList<Point>();

        currentDraggingPoint.tileX = currentDraggingPoint.x / TILE_SIZE;
        currentDraggingPoint.tileY = currentDraggingPoint.y / TILE_SIZE;

        final int xDifference = currentDraggingPoint.tileX - startDraggingPoint.tileX;
        final int yDifference = currentDraggingPoint.tileY - startDraggingPoint.tileY;

        // add left/right offsets
        if (xDifference > 0) {
            for (int i = startDraggingPoint.tileX; i <= currentDraggingPoint.tileX; i++) {
                final Point point = new Point();
                point.x = i * TILE_SIZE;
                point.y = startDraggingPoint.y;
                point.tileX = i;
                point.tileY = startDraggingPoint.tileY;
                draggedPoints.add(point);
            }
        } else {
            for (int i = startDraggingPoint.tileX; i >= currentDraggingPoint.tileX; i--) {
                final Point point = new Point();
                point.x = i * TILE_SIZE;
                point.y = startDraggingPoint.y;
                point.tileX = i;
                point.tileY = startDraggingPoint.tileY;
                draggedPoints.add(point);
            }
        }

        // add up/down offsets
        if (yDifference > 0) {
            for (int i = startDraggingPoint.tileY; i <= currentDraggingPoint.tileY; i++) {
                final Point point = new Point();
                point.x = currentDraggingPoint.x;
                point.y = i * TILE_SIZE;
                point.tileX = currentDraggingPoint.tileX;
                point.tileY = i;
                draggedPoints.add(point);
            }
        } else {
            for (int i = startDraggingPoint.tileY; i >= currentDraggingPoint.tileY; i--) {
                final Point point = new Point();
                point.x = currentDraggingPoint.x;
                point.y = i * TILE_SIZE;
                point.tileX = currentDraggingPoint.tileX;
                point.tileY = i;
                draggedPoints.add(point);
            }
        }

        return draggedPoints;
    }

    public static List<Road> createDraggedRoads(CityBuildingGame game, List<Point> draggedPoints) {
        final List<Road> draggedRoads = new ArrayList<Road>();

        for (Point point : draggedPoints) {
            Road building = new Road(game, 1, "road");
            building.x = point.x;
            building.y = point.y;
            draggedRoads.add(building);
        }

        return draggedRoads;
    }

}
