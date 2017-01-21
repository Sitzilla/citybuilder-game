package com.evansitzes.game.helpers;

/**
 * Created by evan on 1/10/17.
 */
public class Point {
    public int x;
    public int y;
    public int tileX;
    public int tileY;

    public Point() {
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                ", tileX=" + tileX +
                ", tileY=" + tileY +
                '}';
    }
}
