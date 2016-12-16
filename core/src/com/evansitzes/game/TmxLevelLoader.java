package com.evansitzes.game;

import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;

import java.util.HashMap;

/**
 * Created by evan on 12/13/16.
 */
public class TmxLevelLoader {

    private static final String ENVIRONMENT_ENTITIES_PATH = "com.evansitzes.game.entity.environment";
    private static final String ENEMY_ENTITY_PATH = "com.evansitzes.game.entity.enemy";
    private static final String NPC_ENTITY_PATH = "com.evansitzes.game.entity.npc";

    public static Level load(final Vector2 gridPosition, final CityBuildingGame game, final GameScreen gameScreen, final String zone) {
        final TiledMap map = loadMap(zone);
        final Level level = new Level();

        setDefaults(level);
        level.name = (String) map.getProperties().get("name");
        level.level.set(gridPosition.x, gridPosition.y);

        final TiledMapTileLayer tiledMapTileLayer = (TiledMapTileLayer) map.getLayers().get("GroundBottomLayer");

        final MapLayer objectLayer = map.getLayers().get("objects");

//        final MapObjects allObjects = objectLayer.getObjects();
//        final Array<RectangleMapObject> objects = allObjects.getByType(RectangleMapObject.class);
//        final Array<MapObject> objects = allObjects.getByType(MapObject.class);

        level.map = map;
        level.mapHeight = map.getProperties().get("height", Integer.class);
        level.mapWidth = map.getProperties().get("width", Integer.class);
        level.tileHeight = map.getProperties().get("tileheight", Integer.class);
        level.tileWidth = map.getProperties().get("tilewidth", Integer.class);
//        level.length =  tiledMapTileLayer.getHeight() * level.tileSize;
        level.velocity.set(0, 32);

        // TODO create wall tiles
        for (final MapLayer layer : map.getLayers()) {
            if (!layer.getName().contains("Wall")) {
                continue;
            }

            System.out.println(layer);
        }

        return mapObjectsToEntity(game, level, gameScreen);
    }

    private static Level mapObjectsToEntity(final CityBuildingGame game, final Level level, final GameScreen gameScreen) {
//        , final Array<RectangleMapObject> objects
//        for(final RectangleMapObject object : objects) {
            final HashMap<String, String> entityParameters = new HashMap<String, String>();
//            final MapProperties properties = object.getProperties();
//            final String type = (String) properties.get("type");

//        }

        return level;
    }

    private static void setDefaults(final Level level) {
        level.position.set(0, 0);
        level.velocity.set(0, 0);
    }

    private static TiledMap loadMap(final String zone) {
        final String resource = "maps/" + zone + ".tmx";
        final TmxMapLoader tmxMapLoader = new TmxMapLoader(new InternalFileHandleResolver());
         return tmxMapLoader.load(resource);
    }
}
