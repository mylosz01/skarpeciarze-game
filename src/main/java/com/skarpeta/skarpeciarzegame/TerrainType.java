package com.skarpeta.skarpeciarzegame;

enum TerrainType {
    DESERT(new ColorShades("#28c074", "#10908E", "#48e094")),
    GRASS_LAND(new ColorShades("#FFCF8E", "#E7B36F", "#FFE2A3")),
    MOUNTAINS(new ColorShades("#928FB8", "#5B537D", "#B2AFD8")),
    WATER(new ColorShades("#488BD4", "#3C67D3", "#68ABF4"));

    private final ColorShades color;

    TerrainType(ColorShades color) {
        this.color = color;
    }

    public ColorShades getColor() {
        return color;
    }
}
