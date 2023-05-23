package com.skarpeta.skarpeciarzegame.tools;

import javafx.scene.paint.Color;

/** Klasa przechowywująca odcienie pojedynczego koloru */
public class ColorShades {
    /** Podstawowy kolor danej palety */
    public Color primary;
    /** Kolor ciemniejszy od podstawowego - służy do tworzenia cieni */
    public Color darker;
    /** Najjaśniejszy kolor danej palety */
    public Color accent;
    public ColorShades(String primary, String darker, String accent) {
        this.primary = Color.valueOf(primary);
        this.darker = Color.valueOf(darker);
        this.accent = Color.valueOf(accent);
    }
}
