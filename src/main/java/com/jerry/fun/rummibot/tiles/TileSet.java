package com.jerry.fun.rummibot.tiles;

import com.jerry.fun.rummibot.rules.TileRule;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TileSet {

    private Map<Tile, Integer> tiles;
    private int tilesCount;
    private Set<Joker> jokers;

    private static SecureRandom random = new SecureRandom();


    public TileSet() {
        tiles = new HashMap<>();
        jokers = new HashSet<>();

        for (int code : TileRule.getColorCodes()) {
            for (int number : TileRule.getTileNumbers()) {
                tiles.put(new Tile(number, code), 2);
                tilesCount += 2;
            }
        }

        jokers.add(new Joker());
        jokers.add(new Joker());
    }

    public boolean removeTile(Tile tile) {
        if (tiles.get(tile) > 0) {
            tiles.put(tile, tiles.get(tile) - 1);
            tilesCount -= 1;
            return true;
        }
        return false;
    }

    public Tile draw() {
        if (tilesCount == 0) {
            throw new IllegalArgumentException("The set of tiles is empty, cannot be drawn.");
        }

        for (Tile tile : tiles.keySet()) {
            if (removeTile(tile)) {
                return tile;
            }
        }
        return null;
    }

    public int getTileCount(Tile tile) {
        return tiles.get(tile);
    }

    public int getRemainTilesCount() {
        return tilesCount;
    }
}
