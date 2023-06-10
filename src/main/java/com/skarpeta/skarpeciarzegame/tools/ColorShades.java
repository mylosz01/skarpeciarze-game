package com.skarpeta.skarpeciarzegame.tools;

import javafx.scene.paint.Color;

/** Klasa przechowywująca odcienie pojedynczego koloru */
public class ColorShades {
    /** Podstawowy kolor danej palety */
    public Color primary;
    /** Kolor ciemniejszy od podstawowego - służy do tworzenia cieni */
    public Color darker;
    /** Kolor pomiędzy */
    public Color midway;
    public ColorShades(String primary, String darker, String midway) {
        this.primary = Color.valueOf(primary);
        this.darker = Color.valueOf(darker);
        this.midway = Color.valueOf(midway);
    }
}
