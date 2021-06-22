package com.jerry.fun.rummibot.melds;

import com.jerry.fun.rummibot.collectors.Table;
import com.jerry.fun.rummibot.enums.TileColor;
import com.jerry.fun.rummibot.exceptions.InvalidMeldException;
import com.jerry.fun.rummibot.rules.MeldRule;
import com.jerry.fun.rummibot.tiles.Tile;

import java.util.*;

public class RunMeld extends Meld {

    private Table tableDelegate;

    public RunMeld(List<Tile> tiles) {
        this.tiles = new TreeSet<>((o1, o2) -> {
            int numberCmp = Integer.compare(o1.getNumber(), o2.getNumber());
            if (numberCmp == 0) {
                return Integer.compare(o1.getColor().getCode(), o2.getColor().getCode());
            }
            return numberCmp;
        });

        this.tiles.addAll(tiles);

        if (this.tiles.size() != tiles.size()) {
            throw new InvalidMeldException("Given tiles have the same tile");
        }
        selfVerify();
    }

    public RunMeld(List<Tile> tiles, Table tableDelegate) {
        this(tiles);
        this.tableDelegate = tableDelegate;
    }

    @Override
    public boolean isSplittable() {
        return this.tiles.size() > MeldRule.RUN_MIN_TILE * 2;
    }

    @Override
    public List<Meld> split() {
        if (!isSplittable() || this.tiles.size() < 6) {
            return null;
        }

        int splitSize = this.tiles.size() / MeldRule.RUN_MIN_TILE;
        List<Meld> splitMelds = new ArrayList<>();

        Tile[] tileArr = new Tile[this.tiles.size()];
        tileArr = this.tiles.toArray(tileArr);

        Meld lastMeld = null;
        int i = 0;
        while (i < splitSize) {
            int j = i * 3;
            lastMeld = new RunMeld(List.of(tileArr[j], tileArr[j + 1], tileArr[j + 2]));
            splitMelds.add(lastMeld);
            i++;
        }
        System.out.println(lastMeld);

        for (i = i * MeldRule.RUN_MIN_TILE; i < this.tiles.size(); i++) {
            lastMeld.addTile(tileArr[i]);
        }

        return splitMelds;
    }

    @Override
    public boolean isRemovable() {
        return this.tiles.size() > MeldRule.RUN_MIN_TILE;
    }


    @Override
    public boolean removeTile(Tile tile) {
        if (!isRemovable()) {
            return false;
        }

        Tile[] tileArr = new Tile[this.tiles.size()];
        tileArr = this.tiles.toArray(tileArr);

        if (!tile.equals(tileArr[0]) || !tile.equals(tileArr[this.tiles.size() - 1])) {
            return false;
        }
        boolean result = this.tiles.remove(tile);
        selfVerify();

        return result;
    }

    @Override
    public List<Tile> getRemovableTiles() {
        if (isRemovable()) {
            Tile[] tileArr = new Tile[this.tiles.size()];
            tileArr = this.tiles.toArray(tileArr);

            Tile[] finalTileArr = tileArr;
            return new ArrayList<>() {{
                add(finalTileArr[0]);
                add(finalTileArr[finalTileArr.length - 1]);
            }};
        }
        return new ArrayList<>();
    }

    @Override
    public boolean isAddable() {
        return tiles.size() < MeldRule.RUN_MAX_TILE;
    }

    @Override
    public boolean addTile(Tile tile) {
        if (!fit(tile)) {
            return false;
        }

        if (findAddableSideTiles().contains(tile)) {
            tiles.add(tile);
            return true;
        }

        List<Tile> run1 = new ArrayList<>();
        List<Tile> run2 = new ArrayList<>();
        boolean addToRun1 = true;
        for (Tile t : this.tiles) {
            if (t.equals(tile)) {
                addToRun1 = false;
                run1.add(tile);
            }
            if (addToRun1) {
                run1.add(t);
            } else {
                run2.add(t);
            }
        }

        if (addToRun1) {
            throw new IllegalArgumentException("addToRun1 should be false.");
        }

        tableDelegate.removeMeld(this);
        tableDelegate.addMeld(new RunMeld(run1));
        tableDelegate.addMeld(new RunMeld(run2));

        return false;
    }

    @Override
    public boolean fit(Tile tile) {
        if (!isAddable()
                // get the first item in tiles set, to check if the given tile has the same color as the tile in the set
                || tile.getColor() != tiles.iterator().next().getColor()) {
            return false;
        }

        Set<Tile> addableTiles = findAddableTiles();
        return addableTiles.contains(tile);
    }

    @Override
    public void selfVerify() {

        if (tiles.size() < MeldRule.RUN_MIN_TILE || tiles.size() > MeldRule.RUN_MAX_TILE) {
            throw new InvalidMeldException();
        }

        Iterator<Tile> it = tiles.iterator();
        Tile pre = it.next();
        TileColor color = pre.getColor();
        while (it.hasNext()) {
            Tile curr = it.next();
            if (curr.getColor() != color) {
                throw new InvalidMeldException("Colors are the same");
            }
            if (curr.getNumber() - 1 != pre.getNumber()) {
                throw new InvalidMeldException("Run is not in order");
            }
            pre = curr;
        }

    }

    public void setTableDelegate(Table tableDelegate) {
        this.tableDelegate = tableDelegate;
    }

    /**
     * Find addable side tiles <br/>
     * <p>
     * e.g.1 <br/>
     * If a run has Red{2,3,4},
     * the side tiles are Red{1} and Red{5}.
     * </p>
     * <p>
     * e.g.2 <br/>
     * If a run has Blue{1,2,3},
     * the side tile is only Blue{4}
     * </p>
     *
     * @return the tiles which can be added into this Run
     */
    private Set<Tile> findAddableSideTiles() {
        Set<Tile> addableTiles = new HashSet<>();
        List<Tile> currTiles = new ArrayList<>(this.tiles);
        TileColor color = currTiles.get(0).getColor();

        Tile first = currTiles.get(0);
        Tile last = currTiles.get(tiles.size() - 1);

        if (first.getNumber() > 1) {
            addableTiles.add(new Tile(first.getNumber() - 1, color));
        }

        if (last.getNumber() < 13) {
            addableTiles.add(new Tile(last.getNumber() + 1, color));
        }

        return addableTiles;
    }

    private Set<Tile> findAddableTiles() {
        Set<Tile> addableTiles = new HashSet<>(findAddableSideTiles());
        List<Tile> currTiles = new ArrayList<>(this.tiles);
        for (int i = 2; i < currTiles.size() - 2; i++) {
            addableTiles.add(currTiles.get(i));
        }

        return addableTiles;
    }

}
