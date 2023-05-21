package com.skarpeta.skarpeciarzegame;

import javafx.scene.paint.Color;

public class ColorShades {
    Color primary;
    Color darker;
    Color accent;
    public ColorShades(String primary, String darker, String accent) {
        this.primary = Color.valueOf(primary);
        this.darker = Color.valueOf(darker);
        this.accent = Color.valueOf(accent);
    }
}
