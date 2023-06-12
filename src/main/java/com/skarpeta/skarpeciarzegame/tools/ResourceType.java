package com.skarpeta.skarpeciarzegame.tools;

import com.skarpeta.skarpeciarzegame.resources.ForestResource;
import com.skarpeta.skarpeciarzegame.resources.Resource;
import com.skarpeta.skarpeciarzegame.resources.StoneResource;

public enum ResourceType {
    EMPTY,
    FOREST,
    STONE;

    public Resource newResource() {
        return switch (this) {
            case EMPTY -> null;
            case FOREST -> new ForestResource();
            case STONE -> new StoneResource();
        };
    }
}
