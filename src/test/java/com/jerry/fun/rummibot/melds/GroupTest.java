package com.jerry.fun.rummibot.melds;

import com.jerry.fun.rummibot.enums.TileColor;
import com.jerry.fun.rummibot.exceptions.InvalidMeldException;
import com.jerry.fun.rummibot.tiles.Tile;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GroupTest {

    Tile BK13 = new Tile(13, TileColor.BLACK);
    Tile OR13 = new Tile(13, TileColor.ORANGE);
    Tile BL13 = new Tile(13, TileColor.BLUE);
    Tile RE13 = new Tile(13, TileColor.RED);
    Tile RE5 = new Tile(5, TileColor.RED);


    @Test
    public void testEqualGroup() {

        Meld g1 = new GroupMeld(List.of(BL13, OR13, BK13));
        Meld g2 = new GroupMeld(List.of(OR13, BK13, BL13));
        assertEquals(g2, g1);

        Meld g3 = new GroupMeld(List.of(OR13, BK13, BL13, RE13));
        assertNotEquals(g2, g3);
    }

    @Test
    public void testInvalidGroups() {
        assertThrows(InvalidMeldException.class, () -> new GroupMeld(List.of(BK13, OR13, RE13, RE13)), "Has same color");
        assertThrows(InvalidMeldException.class, () -> new GroupMeld(List.of(BK13, OR13, RE5)), "Has different numbers");
        assertThrows(InvalidMeldException.class, () -> new GroupMeld(List.of(BK13, OR13, RE5, BK13, RE13)), "Has too many tiles");
        assertThrows(InvalidMeldException.class, () -> new GroupMeld(List.of(BK13, OR13, RE5)), "Has too few tiles");
    }

    @Test
    public void testValidGroups() {
        Meld m1 = new GroupMeld(List.of(BK13, BL13, RE13));
        Meld m2 = new GroupMeld(List.of(BK13, OR13, RE13, BL13));

        System.out.println(m1.toString());
        assertEquals(m1.getMeldSize(), 3);
        assertTrue(m1.hasTile(BK13));
        assertTrue(m1.hasTile(BL13));
        assertFalse(m1.hasTile(RE5));

        assertEquals(m2.getMeldSize(), 4);
        assertTrue(m2.hasTile(OR13));
        assertTrue(m2.hasTile(BL13));
        assertFalse(m2.hasTile(RE5));

    }

    @Test
    public void testAddTileToGroups() {

        Meld meld = new GroupMeld(List.of(BK13, BL13, RE13));

        assertTrue(meld.isAddable());

        assertFalse(meld.addTile(new Tile(13, TileColor.BLACK)));
        assertFalse(meld.addTile(new Tile(13, TileColor.BLUE)));
        assertFalse(meld.addTile(new Tile(13, TileColor.RED)));

        assertFalse(meld.addTile(new Tile(12, TileColor.BLACK)));
        assertFalse(meld.addTile(new Tile(7, TileColor.ORANGE)));
        assertFalse(meld.addTile(new Tile(11, TileColor.ORANGE)));

        assertTrue(meld.addTile(new Tile(13, TileColor.ORANGE)));
        assertEquals(meld.getMeldSize(), 4);
        assertTrue(meld.hasTile(new Tile(13, TileColor.ORANGE)));
    }

    @Test
    public void testFitTileToGroups() {

        Meld meld = new GroupMeld(List.of(BK13, BL13, RE13));
        assertFalse(meld.fit(BK13));
        assertFalse(meld.fit(BK13));
        assertFalse(meld.fit(RE13));

        assertFalse(meld.fit(new Tile(7, 1)));
        assertFalse(meld.fit(new Tile(9, 3)));
        assertFalse(meld.fit(new Tile(10, 4)));

        assertTrue(meld.fit(new Tile(13, TileColor.ORANGE)));
        assertEquals(meld.getMeldSize(), 3);

    }

    @Test
    public void testRemoveTileFromGroups() {

        Meld meld = new GroupMeld(List.of(BK13, BL13, RE13, OR13));

        assertTrue(meld.isRemovable());
        assertFalse(meld.removeTile(RE5));
        assertFalse(meld.removeTile(new Tile(12, TileColor.ORANGE)));

        // remove one tile
        assertTrue(meld.removeTile(new Tile(13, TileColor.ORANGE)));

        assertFalse(meld.removeTile(new Tile(13, TileColor.BLUE)));
        assertFalse(meld.removeTile(new Tile(13, TileColor.RED)));
        assertFalse(meld.removeTile(new Tile(13, TileColor.BLACK)));

        assertEquals(meld.getMeldSize(), 3);
        assertFalse(meld.isSplittable());

    }

    @Test
    public void groupMeldShouldNotSplit() {
        Meld meld = new GroupMeld(List.of(BK13, BL13, RE13, OR13));
        assertFalse(meld.isSplittable());
        assertEquals(meld.split().size(), 0);
    }

    @Test
    public void testGetRemovableTiles() {

        Meld group1 = new GroupMeld(List.of(BK13, BL13, RE13, OR13));
        Meld group2 = new GroupMeld(List.of(BK13, BL13, RE13));

        assertEquals(4, group1.findRemovableTiles().size());
        assertTrue(group1.findRemovableTiles().contains(BK13));
        assertTrue(group1.findRemovableTiles().contains(BL13));
        assertTrue(group1.findRemovableTiles().contains(RE13));
        assertTrue(group1.findRemovableTiles().contains(OR13));
        assertEquals(0, group2.findRemovableTiles().size());


    }

    @Test
    public void testFindAddableTiles() {

        Meld group1 = new GroupMeld(List.of(BK13, BL13, RE13, OR13));
        Meld group2 = new GroupMeld(List.of(BK13, BL13, RE13));

        assertEquals(0, group1.findAddableTiles().size());
        assertEquals(1, group2.findAddableTiles().size());
        assertTrue(group2.findAddableTiles().contains(OR13));

    }


}
