package com.evansitzes.game.environment;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.evansitzes.game.CityBuildingGame;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by evan on 12/13/16.
 */
public class Level {
    public String name;
    public final Vector2 level = new Vector2();

    public final Vector2 position = new Vector2();
    public final Vector2 velocity = new Vector2();
    public final List<Event> events = new ArrayList();
    public Event finalEvent;
    public int length;
    public int tileSize = 16;
    public int offsetX;
    public int mapHeight;
    public int mapWidth;
    public int tileHeight;
    public int tileWidth;

    public TiledMap map;

    public void render(final CityBuildingGame game) {
        offsetX = 960;
    }

}

