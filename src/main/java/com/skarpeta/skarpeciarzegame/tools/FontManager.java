package com.skarpeta.skarpeciarzegame.tools;

import com.skarpeta.skarpeciarzegame.app.PlayerUI;
import javafx.scene.text.Font;

public class FontManager {
    private static final Font bigFont = Font.loadFont(PlayerUI.class.getResourceAsStream("/joystix.otf"), 20);
    private static final Font smallFont = Font.loadFont(PlayerUI.class.getResourceAsStream("/joystix.otf"), 14);

    public static Font getBigFont() {
        return bigFont;
    }

    public static Font getSmallFont() {
        return smallFont;
    }
}
