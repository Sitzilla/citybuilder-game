package com.evansitzes.game.state;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by evan on 12/27/16.
 */
public class StructuresEnvelope {
    @JsonProperty
    private List<Structure> structures;

    public StructuresEnvelope() {
    }

    public List<Structure> getStructures() {
        return structures;
    }

    public void setStructures(final List<Structure> structures) {
        this.structures = structures;
    }

    @Override
    public String toString() {
        return "StructuresEnvelope{" +
                "structures=" + structures +
                '}';
    }

}
