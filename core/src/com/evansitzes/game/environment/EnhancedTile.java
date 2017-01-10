package com.evansitzes.game.environment;

import com.evansitzes.game.buildings.Building;
import com.evansitzes.game.state.Tile;

/**
 * Same as the Tile class but with additional member variables
 */
public class EnhancedTile extends Tile {
    private Building building;
    private boolean hasBeenChecked;

    public EnhancedTile(final Tile tile) {
        this.setX(tile.getX());
        this.setY(tile.getY());
        this.setOccupied(tile.isOccupied());
    }

    public EnhancedTile(int x, int y) {
        super(x, y);
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(final Building building) {
        this.building = building;
    }

    public boolean isHasBeenChecked() {
        return hasBeenChecked;
    }

    public void setHasBeenChecked(boolean hasBeenChecked) {
        this.hasBeenChecked = hasBeenChecked;
    }
}
