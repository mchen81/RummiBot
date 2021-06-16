package com.jerry.fun.rummibot.tiles;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TileSetTest {

    @Test
    public void testTilesInitialNumber() {
        TileSet set = new TileSet();
        Assertions.assertEquals(set.getRemainTilesCount(), 104);
    }

    @Test
    public void testRemoveTile() {
        TileSet tileSet = new TileSet();
        tileSet.removeTile(new Tile(10, 1));
        Assertions.assertEquals(tileSet.getRemainTilesCount(), 103);
    }

    @Test
    public void testDraw() {
        TileSet tileSet = new TileSet();
        Tile tile = tileSet.draw();
        tile = tileSet.draw();
        Assertions.assertEquals(tileSet.getRemainTilesCount(), 102);
        Assertions.assertNotNull(tile);
    }


}
