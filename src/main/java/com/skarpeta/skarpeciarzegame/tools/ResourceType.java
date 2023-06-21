package com.skarpeta.skarpeciarzegame.tools;

import com.skarpeta.skarpeciarzegame.resources.ForestResource;
import com.skarpeta.skarpeciarzegame.resources.Resource;
import com.skarpeta.skarpeciarzegame.resources.StoneResource;
import com.skarpeta.skarpeciarzegame.worldmap.TerrainType;

public enum ResourceType {
    EMPTY(null),
    FOREST(TerrainType.GRASS_LAND),
    STONE(TerrainType.MOUNTAINS);

    final TerrainType terrain;

    ResourceType(TerrainType terrain) {
        this.terrain = terrain;
    }

    public Resource newResource() {
        return switch (this) {
            case EMPTY -> null;
            case FOREST -> new ForestResource();
            case STONE -> new StoneResource();
        };
    }

    public TerrainType getTerrain() {
        return terrain;
    }
}
