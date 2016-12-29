package com.evansitzes.game.state;

import com.badlogic.gdx.Gdx;
import com.evansitzes.game.CityBuildingGame;
import com.evansitzes.game.buildings.Building;
import com.evansitzes.game.environment.Tile;
import com.evansitzes.game.environment.TilesMap;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.datatype.joda.JodaModule;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by evan on 10/31/16.
 */
public class StateHelper {

    public static ArrayList<Building> loadBuildingsState(final CityBuildingGame game) {
        final ObjectMapper mapper = new ObjectMapper(new YAMLFactory()); // jackson databind
        mapper.registerModule(new JodaModule());

        try {
            final File file = new File(String.valueOf(Gdx.files.local("state/structures.yml")));

            final StructuresEnvelope structuresEnvelope = mapper.readValue(file, StructuresEnvelope.class);
            final ArrayList<Building> buildings = new ArrayList<Building>();

            // If no structures then return an empty list
            if (structuresEnvelope.getStructures() == null) {
                return buildings;
            }

            for (final Structure structure : structuresEnvelope.getStructures()) {
                final Building building = new Building(game, structure.getTileSize(), structure.getSpriteName());
                building.x = structure.getX();
                building.y = structure.getY();
                buildings.add(building);
            }

            return buildings;

        } catch (final IOException e) {
            System.out.println(e);
        }

        return null;
    }

    public static void saveBuildingsState(final ArrayList<Building> buildings) {
        final ObjectMapper mapper = new ObjectMapper(new YAMLFactory()); // jackson databind
        mapper.registerModule(new JodaModule());

        try {
            final File file = new File(String.valueOf(Gdx.files.local("state/structures.yml")));
//            final StructuresEnvelope structuresEnvelope = mapper.readValue(file, StructuresEnvelope.class);
            final StructuresEnvelope structuresEnvelope = new StructuresEnvelope();
            final ArrayList<Structure> structures = new ArrayList<Structure>();
            structuresEnvelope.setStructures(structures);

            for (final Building building : buildings) {
                final Structure structure = new Structure();
                structure.setSpriteName(building.name);
                structure.setTileSize(building.tileSize);
                structure.setX(building.x);
                structure.setY(building.y);

                structuresEnvelope.getStructures().add(structure);
            }

            mapper.writeValue(new FileOutputStream(file), structuresEnvelope);
        } catch (final IOException e) {
            System.out.println(e);
        }
    }

    public static TilesMap loadTilesState(final float mapWidth, final float mapHeight, final int tileHeight, final int tileWidth) {
        final ObjectMapper mapper = new ObjectMapper(new YAMLFactory()); // jackson databind
        mapper.registerModule(new JodaModule());

        try {
            final File file = new File(String.valueOf(Gdx.files.local("state/tiles.yml")));
            final TilesEnvelope tilesEnvelope = mapper.readValue(file, TilesEnvelope.class);

            // If an empty map they initialize a new empty Tiles Map
            if (tilesEnvelope.getTiles() == null) {
                return new TilesMap(mapWidth * tileWidth, mapHeight * tileHeight, tileHeight, tileWidth);
            }

            return new TilesMap(tilesEnvelope.getTiles(), mapWidth * tileWidth, mapHeight * tileHeight, tileHeight, tileWidth);

        } catch (final IOException e) {
            System.out.println(e);
        }

        return null;
    }

    public static void saveTilesState(final TilesMap tilesMap) {
        final ObjectMapper mapper = new ObjectMapper(new YAMLFactory()); // jackson databind
        mapper.registerModule(new JodaModule());

        try {
            final File file = new File(String.valueOf(Gdx.files.local("state/tiles.yml")));
            final TilesEnvelope tilesEnvelope = new TilesEnvelope();
            tilesEnvelope.setTiles(new ArrayList<Tile>());

//            ArrayList<Tile> tiles = tilesMap.getFlattenedTiles();
            tilesEnvelope.getTiles().addAll(tilesMap.getFlattenedTiles());

            mapper.writeValue(new FileOutputStream(file), tilesEnvelope);
        } catch (final IOException e) {
            System.out.println(e);
        }
    }


//    public static CharactersEnvelope loadData(){
//        final ObjectMapper mapper = new ObjectMapper(new YAMLFactory()); // jackson databind
//        mapper.registerModule(new JodaModule());
//
//        try {
//            final File file = new File(String.valueOf(Gdx.files.local("resources/player.yml")));
//            return mapper.readValue(file, CharactersEnvelope.class);
//        } catch (final IOException e) {
//            System.out.println(e);
//        }
//
//        return null;
//    }
//
//    public static ArticlesEnvelope loadItemMap() {
//        final ObjectMapper mapper = new ObjectMapper(new YAMLFactory()); // jackson databind
//        mapper.registerModule(new JodaModule());
//
//        try {
//            final File file = new File(String.valueOf(Gdx.files.local("resources/article.yml")));
//            return mapper.readValue(file, ArticlesEnvelope.class);
//        } catch (final IOException e) {
//            System.out.println(e);
//        }
//
//        return null;
//    }
//
//    //TODO combine these
//    public static void saveEquipment(final ArrayList<String> equipment, final ArrayList<String> inventory) {
//        final ObjectMapper mapper = new ObjectMapper(new YAMLFactory()); // jackson databind
//        mapper.registerModule(new JodaModule());
//
//        try {
//            final File file = new File(String.valueOf(Gdx.files.local("resources/player.yml")));
//            final CharactersEnvelope charactersEnvelope = mapper.readValue(file, CharactersEnvelope.class);
//
//            charactersEnvelope.getCharacters().get(0).setEquipment(equipment);
//            charactersEnvelope.getCharacters().get(0).setInventory(inventory);
//
//
//            mapper.writeValue(new FileOutputStream(file), charactersEnvelope);
//        } catch (final IOException e) {
//            System.out.println(e);
//        }
//    }
//
//    public static void saveGold(final int gold) {
//        final ObjectMapper mapper = new ObjectMapper(new YAMLFactory()); // jackson databind
//        mapper.registerModule(new JodaModule());
//
//        try {
//            final File file = new File(String.valueOf(Gdx.files.local("resources/player.yml")));
//            final CharactersEnvelope charactersEnvelope = mapper.readValue(file, CharactersEnvelope.class);
//
//            charactersEnvelope.getCharacters().get(0).setGold(gold);
//            mapper.writeValue(new FileOutputStream(file), charactersEnvelope);
//        } catch (final IOException e) {
//            System.out.println(e);
//        }
//    }
}
