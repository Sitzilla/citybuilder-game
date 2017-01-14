package com.evansitzes.game.helpers;

/**
 * Created by evan on 1/10/17.
 */
public class PointHelper {
    //TODO currently hardcoded
    private static int TILE_SIZE = 32;

    public static Point getCornerTileFromMiddleArea(final int screenX, final int screenY, final int numberOfTiles) {
        final Point bottomLeftCorner = new Point();
        // tile group has a definite tile as the center
        if (numberOfTiles % 2 != 0) {
            bottomLeftCorner.x = screenX / TILE_SIZE * TILE_SIZE - TILE_SIZE * (numberOfTiles / 2);
            bottomLeftCorner.y = screenY / TILE_SIZE * TILE_SIZE - TILE_SIZE * (numberOfTiles / 2);
            return bottomLeftCorner;
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

            bottomLeftCorner.x = screenX / TILE_SIZE * TILE_SIZE - TILE_SIZE * ((numberOfTiles / 2) - 1);
            bottomLeftCorner.y = screenY / TILE_SIZE * TILE_SIZE - TILE_SIZE * ((numberOfTiles / 2) - 1);
            return bottomLeftCorner;
        }

        // In lower-right quadrant
        if (screenX >= midPointX && screenX < upperBoundX &&
                screenY < midPointY && screenY > lowerBoundY) {

            bottomLeftCorner.x = screenX / TILE_SIZE * TILE_SIZE - TILE_SIZE * (numberOfTiles / 2);
            bottomLeftCorner.y = screenY / TILE_SIZE * TILE_SIZE - TILE_SIZE * ((numberOfTiles / 2) - 1);
            return bottomLeftCorner;
        }

        // In upper-left quadrant
        if (screenX < midPointX && screenX > lowerBoundX &&
                screenY >= midPointY && screenY < upperBoundY) {
            bottomLeftCorner.x = screenX / TILE_SIZE * TILE_SIZE - TILE_SIZE * ((numberOfTiles / 2) - 1);
            bottomLeftCorner.y = screenY / TILE_SIZE * TILE_SIZE - TILE_SIZE * (numberOfTiles / 2);
            return bottomLeftCorner;
        }

        // In upper-right quadrant
        if (screenX >= midPointX && screenX < upperBoundX &&
                screenY >= midPointY && screenY < upperBoundY) {
            bottomLeftCorner.x = screenX / TILE_SIZE * TILE_SIZE - TILE_SIZE * (numberOfTiles / 2);
            bottomLeftCorner.y = screenY / TILE_SIZE * TILE_SIZE - TILE_SIZE * (numberOfTiles / 2);
            return bottomLeftCorner;
        }

//        // Middle of the quadrant
//        if (screenX == midPointX || screenY == midPointY) {
//            bottomLeftCorner.x
//        }

        // User clicked outside of the map
        bottomLeftCorner.x = -1;
        bottomLeftCorner.y = -1;
        return bottomLeftCorner;
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
}
