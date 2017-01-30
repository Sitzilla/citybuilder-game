package com.evansitzes.game.state;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by evan on 1/15/17.
 */
public class Definition {

    @JsonProperty
    private String name;

    @JsonProperty("pretty_name")
    private String prettyName;

    @JsonProperty("type")
    private String type;

    @JsonProperty
    private String description;

    @JsonProperty("tile_size")
    private int tileSize;

    @JsonProperty("max_employees")
    private int maxEmployees;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getPrettyName() {
        return prettyName;
    }

    public void setPrettyName(final String prettyName) {
        this.prettyName = prettyName;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public int getTileSize() {
        return tileSize;
    }

    public void setTileSize(final int tileSize) {
        this.tileSize = tileSize;
    }

    public int getMaxEmployees() {
        return maxEmployees;
    }

    public void setMaxEmployees(final int maxEmployees) {
        this.maxEmployees = maxEmployees;
    }

    @Override
    public String toString() {
        return "Definition{" +
                "name='" + name + '\'' +
                ", prettyName='" + prettyName + '\'' +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", tileSize='" + tileSize + '\'' +
                ", maxEmployees=" + maxEmployees +
                '}';
    }
}
