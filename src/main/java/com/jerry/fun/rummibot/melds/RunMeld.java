package com.jerry.fun.rummibot.melds;

import com.jerry.fun.rummibot.tiles.Tile;

import java.util.List;

public class RunMeld extends Meld {
    @Override
    public boolean isSplittable() {
        return false;
    }

    @Override
    public List<Tile> getSplittableTiles() {
        return null;
    }

    @Override
    public boolean removeTile(Tile tile) {
        return false;
    }

    @Override
    public boolean isAddable() {
        return false;
    }

    @Override
    public boolean addTile(Tile tile) {
        return false;
    }

    @Override
    public boolean fit(Tile tile) {
        return false;
    }

    @Override
    public void selfVerify() {

    }
}
