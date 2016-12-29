package com.evansitzes.game.state;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

/**
 * Created by evan on 12/27/16.
 */
public class StructuresEnvelope {
    @JsonProperty
    private ArrayList<Structure> structures;

    public StructuresEnvelope() {
    }

    public ArrayList<Structure> getStructures() {
        return structures;
    }

    public void setStructures(ArrayList<Structure> structures) {
        this.structures = structures;
    }

    @Override
    public String toString() {
        return "StructuresEnvelope{" +
                "structures=" + structures +
                '}';
    }

}
