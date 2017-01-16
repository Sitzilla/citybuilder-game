package com.evansitzes.game.state;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

/**
 * Created by evan on 1/15/17.
 */
public class DefinitionsEnvelope {
    @JsonProperty
    private ArrayList<Definition> definitions;

    public DefinitionsEnvelope() {
    }

    public ArrayList<Definition> getDefinitions() {
        return definitions;
    }

    public void setDefinitions(final ArrayList<Definition> definitions) {
        this.definitions = definitions;
    }

//    public Definition getDefinitions

    @Override
    public String toString() {
        return "DefinitionsEnvelope{" +
                "definitions=" + definitions +
                '}';
    }
}
