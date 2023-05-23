package com.skarpeta.skarpeciarzegame.tools;

import com.skarpeta.skarpeciarzegame.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlayerManager {
    private static List<Player> players = Collections.synchronizedList(new ArrayList<Player>());
    public static void addPlayer(Player player) {
        players.add(player);
    }
    public static Player getPlayer() {
        return players.get(0);
    }
}
