package com.evansitzes.game.state;

import com.badlogic.gdx.Gdx;
import com.evansitzes.game.CityBuildingGame;
import com.evansitzes.game.buildings.Building;
import com.evansitzes.game.buildings.EmployableBuilding;
import com.evansitzes.game.buildings.House;
import com.evansitzes.game.buildings.Road;
import com.evansitzes.game.environment.EnhancedTile;
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
public class SaveStateHelper {

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

            //TODO definitely make this more efficient
            for (final Structure structure : structuresEnvelope.getStructures()) {
                final Building building;
                if (structure.getSpriteName().equals("house")) {
                    building = new House(game, structure.getTileSize(), structure.getSpriteName(), structure.getPrettyName());
                } else if (structure.getSpriteName().equals("guard_house")) {
                    building = new EmployableBuilding(game, structure.getTileSize(), structure.getSpriteName(), structure.getPrettyName());
                } else if (structure.getSpriteName().equals("road")) {
                    building = new Road(game, structure.getTileSize(), structure.getSpriteName(), structure.getPrettyName());
                } else {
                  building = new Building(game, structure.getTileSize(), structure.getSpriteName(), structure.getPrettyName());
                }
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
                structure.setPrettyName(building.prettyName);
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

    public static TilesMap loadTilesState(final float mapWidth, final float mapHeight, final int tileHeight, final int tileWidth, final ArrayList<Building> buildings) {
        final ObjectMapper mapper = new ObjectMapper(new YAMLFactory()); // jackson databind
        mapper.registerModule(new JodaModule());

        try {
            final File file = new File(String.valueOf(Gdx.files.local("state/tiles.yml")));
            final TilesEnvelope tilesEnvelope = mapper.readValue(file, TilesEnvelope.class);

            // If an empty map they initialize a new empty Tiles Map
            if (tilesEnvelope.getTiles() == null) {
                final TilesMap tilesMap = new TilesMap(mapWidth * tileWidth, mapHeight * tileHeight, tileHeight, tileWidth);
                tilesMap.addAllBuildings(buildings);
                return tilesMap;
            }

            // Change all Tiles objects to EnhancedTile objects
            final ArrayList<EnhancedTile> enhancedTiles = new ArrayList<EnhancedTile>();
            for (final Tile tile : tilesEnvelope.getTiles()) {
                enhancedTiles.add(new EnhancedTile(tile));
            }

            final TilesMap tilesMap = new TilesMap(enhancedTiles, mapWidth * tileWidth, mapHeight * tileHeight, tileHeight, tileWidth);
            tilesMap.addAllBuildings(buildings);

            return tilesMap;

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

}
