package com.evansitzes.game.state;

import com.badlogic.gdx.Gdx;
import com.evansitzes.game.CityBuildingGame;
import com.evansitzes.game.buildings.*;
import com.evansitzes.game.environment.EnhancedTile;
import com.evansitzes.game.environment.TilesMap;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.datatype.joda.JodaModule;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by evan on 10/31/16.
 */
public class SaveStateHelper {

    public static List<Building> loadBuildingsState(final CityBuildingGame game, final Map<String, Definition> definitions, final int mapTileSize) {
        final ObjectMapper mapper = new ObjectMapper(new YAMLFactory()); // jackson databind
        mapper.registerModule(new JodaModule());

        try {
            final File file = new File(String.valueOf(Gdx.files.local("state/structures.yml")));

            final StructuresEnvelope structuresEnvelope = mapper.readValue(file, StructuresEnvelope.class);
            final List<Building> buildings = new ArrayList<Building>();

            // If no structures then return an empty list
            if (structuresEnvelope.getStructures() == null) {
                return buildings;
            }

            //TODO definitely make this more efficient
            for (final Structure structure : structuresEnvelope.getStructures()) {
                final Definition buildingDefinition = definitions.get(structure.getSpriteName());
                final Building building;

                if (buildingDefinition.getType().equals("house")) {
                    building = buildHouse(game, structure, buildingDefinition);
                } else if (buildingDefinition.getType().equals("farmhouse")) {
                    building = buildFarmhouse(game, structure, buildingDefinition, mapTileSize);
                } else if (buildingDefinition.getType().equals("employableBuilding")) {
                    building = buildEmployableBuilding(game, structure, buildingDefinition);
                } else if (buildingDefinition.getType().equals("road")) {
                    building = new Road(game, structure.getTileSize(), buildingDefinition.getType());
                } else {
                  building = new Building(game, structure.getTileSize(), buildingDefinition.getType());
                }
                building.name = buildingDefinition.getName();
                building.prettyName = buildingDefinition.getPrettyName();
                building.description = buildingDefinition.getDescription();
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

    private static House buildHouse(final CityBuildingGame game, final Structure structure, final Definition definition) {
        return new House(game, structure.getTileSize(), definition.getType());
    }

    private static Farmhouse buildFarmhouse(final CityBuildingGame game, final Structure structure, final Definition definition, final int mapTileSize) {
        final Farmhouse building = new Farmhouse(game, structure.getTileSize(), definition.getType(), mapTileSize);
        building.maxEmployability = definition.getMaxEmployees();
        return building;
    }

    private static EmployableBuilding buildEmployableBuilding(final CityBuildingGame game, final Structure structure, final Definition definition) {
        final EmployableBuilding building = new EmployableBuilding(game, structure.getTileSize(), definition.getType());
        building.maxEmployability = definition.getMaxEmployees();
        return building;
    }

    public static void saveBuildingsState(final List<Building> buildings) {
        final ObjectMapper mapper = new ObjectMapper(new YAMLFactory()); // jackson databind
        mapper.registerModule(new JodaModule());

        try {
            final File file = new File(String.valueOf(Gdx.files.local("state/structures.yml")));
//            final StructuresEnvelope structuresEnvelope = mapper.readValue(file, StructuresEnvelope.class);
            final StructuresEnvelope structuresEnvelope = new StructuresEnvelope();
            final List<Structure> structures = new ArrayList<Structure>();
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

    public static TilesMap loadTilesState(final float mapWidth, final float mapHeight, final int tileHeight, final int tileWidth, final List<Building> buildings) {
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
            final List<EnhancedTile> enhancedTiles = new ArrayList<EnhancedTile>();
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

    public static Map<String, Definition> loadObjectDefinitions() {
        final Map<String, Definition> definitions = new HashMap<String, Definition>();
        final ObjectMapper mapper = new ObjectMapper(new YAMLFactory()); // jackson databind
        mapper.registerModule(new JodaModule());

        try {
            final File file = new File(String.valueOf(Gdx.files.local("configurations/definitions.yml")));

            for (final Definition definition : mapper.readValue(file, DefinitionsEnvelope.class).getDefinitions()) {
                definitions.put(definition.getName(), definition);
            }
            //            final DefinitionsEnvelope definitionsEnvelope = mapper.readValue(file, DefinitionsEnvelope.class);
//            final List<Definition> definitions = new ArrayList<Definition>();Definition

            return definitions;
        } catch (final IOException e) {
            System.out.println(e);
        }

        return null;
    }

}
