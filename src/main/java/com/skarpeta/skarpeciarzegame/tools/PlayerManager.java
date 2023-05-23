package com.skarpeta.skarpeciarzegame.tools;

import com.skarpeta.skarpeciarzegame.Player;

import java.util.ArrayList;

public class PlayerManager {
    public static ArrayList<Player> players = new ArrayList<>();
    public static void addPlayer(Player player) {
        players.add(player);
    }
}
