package com.skarpeta.skarpeciarzegame.tools;

import com.skarpeta.skarpeciarzegame.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/** Przechowywanie graczy po stronie serwera */
public class PlayerManager {
    private static List<Player> players = Collections.synchronizedList(new ArrayList<Player>());
    /** Dodanie gracza który połączył się z serwerem */
    public static void addPlayer(Player player) {
        players.add(player);
    }
    /** Zwraca pierwszego gracza z listy */
    public static Player getPlayer() {
        return players.get(0);
    }
}
