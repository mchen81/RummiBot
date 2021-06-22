package com.jerry.fun.rummibot.melds;

import com.jerry.fun.rummibot.tiles.Tile;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract class Meld {

    protected Set<Tile> tiles;

    public abstract boolean isSplittable();

    public abstract List<Meld> split();

    public abstract boolean isRemovable();

    public abstract boolean removeTile(Tile tile);

    public abstract List<Tile> getRemovableTiles();

    public abstract boolean isAddable();

    public abstract boolean addTile(Tile tile);

    public abstract boolean fit(Tile tile);

    public int getMeldSize() {
        return tiles.size();
    }

    public abstract void selfVerify();

    public List<Tile> getTiles() {
        return new ArrayList<>(tiles);
    }

    public boolean hasTile(Tile tile) {
        return tiles.contains(tile);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Meld meld = (Meld) o;


        return this.getMeldSize() == meld.getMeldSize()
                && tiles.containsAll(meld.tiles)
                && meld.tiles.containsAll(tiles);
    }

    @Override
    public int hashCode() {
        int offset = 99;
        if (this instanceof GroupMeld) {
            offset = 1;
        } else if (this instanceof RunMeld) {
            offset = 2;
        }
        return tiles.stream().mapToInt(Tile::hashCode).sum() * offset;
    }

    @Override
    public String toString() {
        return "Meld{" +
                "tiles=" + tiles +
                '}';
    }
}
