package com.jerry.fun.rummibot.collectors;

import com.jerry.fun.rummibot.tiles.Tile;

import java.util.ArrayList;
import java.util.List;

public class Hand {

    private List<Tile> holdingTiles;


    public void addTile(Tile tile) {
        holdingTiles.add(tile);
    }

    public void addTiles(List<Tile> tiles) {
        holdingTiles.addAll(tiles);
    }

    public boolean removeTile(Tile tile) {
        return holdingTiles.remove(tile);
    }

    public boolean removeTile(List<Tile> tiles) {
        return holdingTiles.removeAll(tiles);
    }

    public void sortByColor() {
        holdingTiles.sort((o1, o2) -> {
            int colorCmp = Integer.compare(o1.getColor().getCode(), o2.getColor().getCode());
            if (colorCmp == 0) {
                return Integer.compare(o1.getNumber(), o2.getNumber());
            }
            return colorCmp;
        });
    }

    public void sortByNumber() {
        holdingTiles.sort((o1, o2) -> {
            int colorCmp = Integer.compare(o1.getNumber(), o2.getNumber());
            if (colorCmp == 0) {
                return Integer.compare(o1.getColor().getCode(), o2.getColor().getCode());
            }
            return colorCmp;
        });
    }

    public List<Tile> getHoldingTiles() {
        return new ArrayList<>(holdingTiles);
    }
}
