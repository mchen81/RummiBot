package com.jerry.fun.rummibot.melds;

import com.jerry.fun.rummibot.enums.TileColor;
import com.jerry.fun.rummibot.exceptions.InvalidMeldException;
import com.jerry.fun.rummibot.rules.MeldRule;
import com.jerry.fun.rummibot.tiles.Tile;

import java.util.*;

/**
 * Same number tiles
 */
public class GroupMeld extends Meld {

    public GroupMeld(List<Tile> tiles) {

        this.tiles = new TreeSet<>((o1, o2) -> {
            int colorCmp = Integer.compare(o1.getColor().getCode(), o2.getColor().getCode());
            if (colorCmp == 0) {
                return Integer.compare(o1.getNumber(), o2.getNumber());
            }
            return colorCmp;
        });

        this.tiles.addAll(tiles);

        if (this.tiles.size() != tiles.size()) {
            throw new InvalidMeldException("Given tiles have the same color");
        }

        selfVerify();
    }

    @Override
    public boolean isSplittable() {
        // A group should not be splittable
        return false;
    }

    @Override
    public List<Meld> split() {
        // Since a group is not splittable, return empty list
        return new ArrayList<>();
    }

    @Override
    public boolean isRemovable() {
        return this.tiles.size() > MeldRule.MELD_MIN_TILE;
    }

    @Override
    public List<Tile> getRemovableTiles() {
        if (!isRemovable() || this.tiles.size() == 3) {
            return new ArrayList<>();
        }
        return new ArrayList<>(this.tiles);
    }

    @Override
    public boolean removeTile(Tile tile) {
        if (!this.isRemovable()) {
            return false;
        }

        if (!this.tiles.contains(tile)) {
            return false;
        }

        this.tiles.remove(tile);
        selfVerify();
        return true;
    }

    @Override
    public boolean isAddable() {
        return getMeldSize() < MeldRule.GROUP_MAX_TILE;
    }

    @Override
    public boolean addTile(Tile tile) {
        if (fit(tile)) {
            tiles.add(tile);
            selfVerify();
            return true;
        }
        return false;
    }

    @Override
    public boolean fit(Tile tile) {

        if (!isAddable()
                // get the first item in tiles set, to check if the given tile has the same number as the tile in the set
                || tile.getNumber().intValue() != tiles.iterator().next().getNumber()) {
            return false;
        }

        // check if tile's color has existed in the set
        for (Tile t : tiles) {
            if (t.getColor() == tile.getColor()) {
                return false;
            }
        }

        GroupMeld origin = new GroupMeld(new ArrayList<>(this.tiles));
        try {
            tiles.add(tile);
            selfVerify();
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            this.tiles = origin.tiles;
        }
    }

    @Override
    public void selfVerify() {
        if (tiles == null ||
                tiles.size() < MeldRule.GROUP_MIN_TILE ||
                tiles.size() > MeldRule.GROUP_MAX_TILE) {
            throw new InvalidMeldException();
        }

        Set<TileColor> colors = new HashSet<>();
        int tileNumber = tiles.iterator().next().getNumber();
        for (Tile tile : tiles) {
            if (tile.getNumber() != tileNumber) {
                throw new InvalidMeldException("Given group has different numbers");
            }
            colors.add(tile.getColor());
        }

        if (tiles.size() != colors.size()) {
            throw new InvalidMeldException("Given group has the same colors");
        }

    }

}
