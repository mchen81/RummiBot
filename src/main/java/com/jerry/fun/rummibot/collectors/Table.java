package com.jerry.fun.rummibot.collectors;

import com.jerry.fun.rummibot.enums.TileColor;
import com.jerry.fun.rummibot.melds.GroupMeld;
import com.jerry.fun.rummibot.melds.Meld;
import com.jerry.fun.rummibot.melds.RunMeld;
import com.jerry.fun.rummibot.tiles.Tile;

import java.util.*;

public class Table {

    private List<Meld> prevMelds;
    private List<Meld> currMelds;
    private boolean undoable = false;

    public Table() {
        prevMelds = new ArrayList<>();
        currMelds = new ArrayList<>();
    }

    public Table(List<Meld> melds) {
        this.prevMelds = new ArrayList<>(melds);
        this.currMelds = new ArrayList<>(melds);
    }

    public boolean removeMeld(Meld meld) {
        prepareMovement();
        return currMelds.remove(meld);
    }

    public boolean addMeld(Meld meld) {
        prepareMovement();
        currMelds.add(meld);

        if (meld instanceof RunMeld) {
            ((RunMeld) meld).setTableDelegate(this);
        }

        return true;
    }

    public boolean addTile(Tile tile) {
        prepareMovement();
        // case1: can add into exist meld
        for (Meld meld : this.currMelds) {
            if (meld.fit(tile)) {
                meld.addTile(tile);
                return true;
            }
        }

        // TODO case2: need to take out some tiles from other melds

        final Set<Tile> sameNumberTiles = new HashSet<>(Set.of(
                new Tile(tile.getNumber(), TileColor.BLACK),
                new Tile(tile.getNumber(), TileColor.BLUE),
                new Tile(tile.getNumber(), TileColor.ORANGE),
                new Tile(tile.getNumber(), TileColor.RED)));
        sameNumberTiles.remove(tile);

        // form a group with two tiles from other melds
        Map<Tile, Meld> candidates = new HashMap<>();
        for (Meld meld : this.currMelds) {
            if (!meld.isRemovable()) {
                continue;
            }
            Set<Tile> removableTiles = meld.findRemovableTiles();
            Set<Tile> groupTiles = new HashSet<>(sameNumberTiles);
            if (groupTiles.retainAll(removableTiles) &&
                    !groupTiles.isEmpty() &&
                    !candidates.containsKey(groupTiles.iterator().next())) {
                candidates.put(groupTiles.iterator().next(), meld);
            }
        }
        Tile[] twoOtherTiles = new Tile[2];
        if (candidates.size() > 1) {
            int c = 0;
            for (Tile candidate : candidates.keySet()) {
                if (c > 1) {
                    break;
                }
                candidates.get(candidate).removeTile(candidate);
                twoOtherTiles[c] = candidate;
                c++;
            }
            this.addMeld(new GroupMeld(List.of(twoOtherTiles[0], twoOtherTiles[1], tile)));
            return true;
        }

        // form a run
        Tile[] runTiles = new Tile[2];
        if (tile.getNumber() == 1) {
            runTiles[0] = new Tile(2, tile.getColor());
            runTiles[1] = new Tile(3, tile.getColor());
        } else if (tile.getNumber() == 13) {
            runTiles[0] = new Tile(12, tile.getColor());
            runTiles[1] = new Tile(11, tile.getColor());
        } else {
            runTiles[0] = new Tile(tile.getNumber() + 1, tile.getColor());
            runTiles[1] = new Tile(tile.getNumber() - 1, tile.getColor());
        }

        candidates = new HashMap<>();
        Set<Tile> neededTiles;
        for (Meld meld : this.currMelds) {
            if (!meld.isRemovable()) {
                continue;
            }
            neededTiles = new HashSet<>(List.of(runTiles[0], runTiles[1]));
            if (neededTiles.retainAll(meld.findRemovableTiles())) {
                for (Tile nt : neededTiles) {
                    // TODO: probably has bug here
                    candidates.put(nt, meld);
                }
            }
        }

        twoOtherTiles = new Tile[2];
        if (candidates.size() > 1) {
            int c = 0;
            for (Tile candidate : candidates.keySet()) {
                if (c > 1) {
                    break;
                }
                if (!candidates.get(candidate).removeTile(candidate)) {
                    throw new IllegalArgumentException("Cannot remove this tile");
                }
                twoOtherTiles[c] = candidate;
                c++;
            }
            this.addMeld(new RunMeld(List.of(twoOtherTiles[0], twoOtherTiles[1], tile)));
            return true;
        }

        return false;
    }


    private void prepareMovement() {
        prevMelds = new ArrayList<>(currMelds);
        undoable = true;
    }

    public void undo() {
        if (undoable) {
            currMelds = prevMelds;
            undoable = false;
        }
        throw new IllegalArgumentException("Cannot undo");
    }


    public boolean hasMeld(Meld meld) {
        return currMelds.contains(meld);
    }

    public List<Meld> getCurrMelds() {
        return new ArrayList<>(currMelds);
    }

    @Override
    public String toString() {

        if (currMelds.isEmpty()) {
            return "Table is empty";
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (Meld m : currMelds) {
            stringBuilder.append(m.toString());
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

}
