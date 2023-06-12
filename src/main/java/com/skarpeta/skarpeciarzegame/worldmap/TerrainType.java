package com.skarpeta.skarpeciarzegame.worldmap;

import com.skarpeta.skarpeciarzegame.tools.ColorShades;

/** Przechowywanie informacji o terenie pól Field
 *  Teren jest definiowany poprzez ColorShades - zbiór kolorów
 */
public enum TerrainType {
    WATER(0, new ColorShades("#488BD4", "#3C67D3", "#68ABF4")),
    DESERT(1,new ColorShades("#FFCF8E", "#E7B36F", "#E7B36F")),
    GRASS_LAND(2, new ColorShades("#28c074", "#10908E", "#19a383")),
    MOUNTAINS(3, new ColorShades("#928FB8", "#5B537D", "#7c75a3"));

    private final ColorShades color;

    private final int index;

    TerrainType(int index, ColorShades color) {
        this.color = color;
        this.index = index;
    }

    public ColorShades getColor() {
        return color;
    }
    public int getIndex() {
        return index;
    }
    /** Zwraca typ terenu podając indeks */
    public static TerrainType fromIndex(int index) {
        for (TerrainType myEnum : TerrainType.values()) {
            if (myEnum.getIndex() == index) {
                return myEnum;
            }
        }
        throw new IllegalArgumentException("Invalid index: " + index);
    }
}
