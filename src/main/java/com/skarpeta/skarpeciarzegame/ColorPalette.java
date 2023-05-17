package com.skarpeta.skarpeciarzegame;

import javafx.scene.paint.Color;

public class ColorPalette {
    Color primary;
    Color light;
    Color dark;

    public ColorPalette(Color primary,Color light, Color dark) {
        this.primary = primary;
        this.light = light;
        this.dark = dark;
    }

    //generates a palette
    public ColorPalette(Color paletteDefiningColor) {
        this.primary = paletteDefiningColor;
        this.light = paletteDefiningColor.saturate().brighter();
        this.dark = paletteDefiningColor.desaturate().darker();
    }
}
