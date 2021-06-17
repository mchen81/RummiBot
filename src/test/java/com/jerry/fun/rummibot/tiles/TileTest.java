package com.jerry.fun.rummibot.tiles;

import com.jerry.fun.rummibot.enums.TileColor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

public class TileTest {

    @Test
    public void testSameTiles() {

        Tile tile1 = new Tile(12, 1);
        Tile tile2 = new Tile(12, 1);
        Tile tile3 = new Tile(12, TileColor.BLACK);

        Assertions.assertEquals(tile2, tile1);
        Assertions.assertEquals(tile1, tile2);
        Assertions.assertEquals(tile1, tile3);
        Assertions.assertEquals(tile2, tile3);
    }

    @Test
    public void testDiffTiles() {
        Tile tile1 = new Tile(13, TileColor.BLUE);
        Tile tile2 = new Tile(5, TileColor.RED);
        Tile tile3 = new Tile(12, TileColor.ORANGE);

        Assertions.assertNotEquals(tile2, tile1);
        Assertions.assertNotEquals(tile1, tile2);
        Assertions.assertNotEquals(tile1, tile3);
        Assertions.assertNotEquals(tile2, tile3);
    }

    @Test
    public void invalidNumberTile() {
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> new Tile(14, 1));
        String expectedMessage = "Tile Number cannot be greater than 13 or less than 1.";
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void invalidNumberColor() {
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> new Tile(12, 0));
        String expectedMessage = "Color code can only be 1,2,3, or 4";
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testTileWithSet() {

        Set<Tile> tileSet = new HashSet<>();
        tileSet.add(new Tile(13, 1));
        tileSet.add(new Tile(13, 2));
        tileSet.add(new Tile(13, 3));
        tileSet.add(new Tile(13, 4));

        Assertions.assertTrue(tileSet.contains(new Tile(13, 1)));
        Assertions.assertTrue(tileSet.contains(new Tile(13, 2)));
        Assertions.assertTrue(tileSet.contains(new Tile(13, 3)));
        Assertions.assertTrue(tileSet.contains(new Tile(13, 4)));

        for (int num = 1; num < 13; num++) {
            for (int color = 1; color <= 4; color++) {
                Assertions.assertFalse(tileSet.contains(new Tile(num, color)));
            }
        }
    }

}
