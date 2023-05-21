package com.skarpeta.skarpeciarzegame.tools;

import javafx.scene.paint.Color;

public class ColorShades {
    public Color primary;
    public Color darker;
    Color accent;
    public ColorShades(String primary, String darker, String accent) {
        this.primary = Color.valueOf(primary);
        this.darker = Color.valueOf(darker);
        this.accent = Color.valueOf(accent);
    }
}
