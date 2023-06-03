package com.skarpeta.skarpeciarzegame.tools;

import com.skarpeta.skarpeciarzegame.network.Player;

import java.util.*;

/** Przechowywanie graczy po stronie serwera */
public class PlayerManager {
    private final Map<Integer,Player> players = Collections.synchronizedMap(new TreeMap<>());
    /** Dodanie gracza który połączył się z serwerem */
    public void addPlayer(int id,Player player) {
        players.put(id,player);
    }
    /** Zwraca pierwszego gracza z listy */
    public Player getPlayer(int id) {
        return players.get(id);
    }

    public void removePlayer(int playerID) {
        players.remove(playerID);
    }
}
