package com.evansitzes.game.helpers;

import com.evansitzes.game.people.sprites.Person.Facing;

/**
Tuple for holding a location in a certain direction from any (x, y) coordinate
 and the number of tiles it is away from the building bottom-left corner
 */
public class Direction {

    public int directionIndex;
    public Facing facingDirection;

    public Direction(final int directionIndex, final Facing facingDirection) {
        this.directionIndex = directionIndex;
        this.facingDirection = facingDirection;
    }
}
