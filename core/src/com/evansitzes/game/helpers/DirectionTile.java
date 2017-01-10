package com.evansitzes.game.helpers;

import com.evansitzes.game.people.sprites.Person.Facing;

/**
 * Created by evan on 1/7/17.
 */
public class DirectionTile {
    public final int x;
    public final int y;

    private final DirectionTile parentTile;
    private final Facing relativeDirectionOfTile;

    public DirectionTile(final DirectionTile parentTile, final Facing relativeDirectionOfTile, final int x, final int y) {
        this.parentTile = parentTile;
        this.relativeDirectionOfTile = relativeDirectionOfTile;
        this.x = x;
        this.y = y;
    }

    public DirectionTile getParentTile() {
        return parentTile;
    }

    public Facing getDirection() {
        return relativeDirectionOfTile;
    }
}
