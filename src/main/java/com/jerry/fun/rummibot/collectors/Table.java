package com.jerry.fun.rummibot.collectors;

import com.jerry.fun.rummibot.melds.Meld;
import com.jerry.fun.rummibot.melds.RunMeld;
import com.jerry.fun.rummibot.tiles.Tile;

import java.util.ArrayList;
import java.util.List;

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
        // TODO
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

}
