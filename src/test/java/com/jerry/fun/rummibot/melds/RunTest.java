package com.jerry.fun.rummibot.melds;

import com.jerry.fun.rummibot.enums.TileColor;
import com.jerry.fun.rummibot.exceptions.InvalidMeldException;
import com.jerry.fun.rummibot.tiles.Tile;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RunTest {

    Tile R3 = new Tile(3, TileColor.RED);
    Tile R4 = new Tile(4, TileColor.RED);
    Tile R5 = new Tile(5, TileColor.RED);
    Tile R6 = new Tile(6, TileColor.RED);

    Tile B7 = new Tile(7, TileColor.BLACK);
    Tile B8 = new Tile(8, TileColor.BLACK);
    Tile B9 = new Tile(9, TileColor.BLACK);
    Tile B10 = new Tile(10, TileColor.BLACK);

    Tile O7 = new Tile(7, TileColor.ORANGE);
    Tile O8 = new Tile(8, TileColor.ORANGE);
    Tile O9 = new Tile(9, TileColor.ORANGE);
    Tile O10 = new Tile(10, TileColor.ORANGE);
    Tile O11 = new Tile(11, TileColor.ORANGE);
    Tile O12 = new Tile(12, TileColor.ORANGE);
    Tile O13 = new Tile(13, TileColor.ORANGE);


    @Test
    public void testEqualRun() {
        Meld r1 = new RunMeld(List.of(R3, R4, R5));
        Meld r2 = new RunMeld(List.of(R5, R3, R4));
        assertEquals(r1, r2);

        Meld r3 = new RunMeld(List.of(O7, O8, O9, O10, O11, O12));
        Meld r4 = new RunMeld(List.of(O12, O7, O8, O10, O11, O9));
        assertEquals(r3, r4);

        Meld r5 = new RunMeld(List.of(O7, O8, O9));
        assertNotEquals(r3, r5);
    }

    @Test
    public void ValidRunTest() {
        Meld r1 = new RunMeld(List.of(R3, R4, R5));
        Meld r2 = new RunMeld(List.of(O11, O10, O13, O12));
        Meld r3 = new RunMeld(List.of(B9, B8, B7));

        assertEquals(r1.getMeldSize(), 3);
        assertEquals(r2.getMeldSize(), 4);
        assertEquals(r3.getMeldSize(), 3);

    }


    @Test
    public void InvalidRunTest() {

        assertThrows(InvalidMeldException.class, () -> new RunMeld(List.of(R3, R4)));
        assertThrows(InvalidMeldException.class, () -> new RunMeld(List.of(B9, O10, O11, O12, O13)));
        assertThrows(InvalidMeldException.class, () -> new RunMeld(List.of(B7, B8, B9, B10, O11, O12)));
        assertThrows(InvalidMeldException.class, () -> new RunMeld(List.of(R6, B10, O11, O12)));
        assertThrows(InvalidMeldException.class, () -> new RunMeld(List.of(O7, O8, O9, B10, O11, O12)));

    }

    @Test
    public void splitRunTest() {
        Meld notSplittableRun = new RunMeld(List.of(R3, R4, R5, R6));
        Meld splittableRun = new RunMeld(List.of(O11, O8, O9, O10, O13, O12, O7));

        assertFalse(notSplittableRun.isSplittable());
        assertTrue(splittableRun.isSplittable());

        List<Meld> splits = splittableRun.split();
        assertEquals(2, splits.size());
        assertEquals(new RunMeld(List.of(O7, O8, O9)), splits.get(0));
        assertEquals(new RunMeld(List.of(O10, O11, O12, O13)), splits.get(1));

    }

    @Test
    public void testGetRemovableTiles(){
        Meld r1 = new RunMeld(List.of(R3, R4, R5, R6));
        Meld r2 = new RunMeld(List.of(O11, O10, O13, O12));
        Meld r3 = new RunMeld(List.of(B9, B8, B7));

        assertEquals(r1.getRemovableTiles().size(), 2);
        assertTrue(r1.getRemovableTiles().contains(R3));
        assertTrue(r1.getRemovableTiles().contains(R6));

        assertEquals(r2.getRemovableTiles().size(), 2);
        assertTrue(r2.getRemovableTiles().contains(O10));
        assertTrue(r2.getRemovableTiles().contains(O13));

        assertEquals(r3.getRemovableTiles().size(), 0);


    }

}
