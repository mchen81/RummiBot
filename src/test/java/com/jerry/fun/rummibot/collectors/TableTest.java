package com.jerry.fun.rummibot.collectors;

import com.jerry.fun.rummibot.enums.TileColor;
import com.jerry.fun.rummibot.melds.GroupMeld;
import com.jerry.fun.rummibot.melds.Meld;
import com.jerry.fun.rummibot.melds.RunMeld;
import com.jerry.fun.rummibot.tiles.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TableTest {

    /**
     * Initial table has:
     * Group(2 with 4 colors)
     * Group(10 with Orange, 2B)
     * Group(9 with Orange, BK, Red)
     * Run(3456 RED)
     * Run(3456 ORANGE)
     * Run(10 11 12 13 BLUE)
     */
    Table table;

    @BeforeEach
    public void setup() {

        Meld g1 = new GroupMeld(List.of(
                new Tile(2, TileColor.ORANGE),
                new Tile(2, TileColor.BLACK),
                new Tile(2, TileColor.BLUE),
                new Tile(2, TileColor.RED)
        ));

        Meld g2 = new GroupMeld(List.of(
                new Tile(10, TileColor.ORANGE),
                new Tile(10, TileColor.BLACK),
                new Tile(10, TileColor.BLUE)
        ));

        Meld g3 = new GroupMeld(List.of(
                new Tile(9, TileColor.ORANGE),
                new Tile(9, TileColor.BLACK),
                new Tile(9, TileColor.RED)
        ));

        Meld r1 = new RunMeld(List.of(
                new Tile(3, TileColor.RED),
                new Tile(4, TileColor.RED),
                new Tile(5, TileColor.RED),
                new Tile(6, TileColor.RED)
        ));

        Meld r2 = new RunMeld(List.of(
                new Tile(3, TileColor.ORANGE),
                new Tile(4, TileColor.ORANGE),
                new Tile(5, TileColor.ORANGE),
                new Tile(6, TileColor.ORANGE)
        ));

        Meld r3 = new RunMeld(List.of(
                new Tile(10, TileColor.BLUE),
                new Tile(11, TileColor.BLUE),
                new Tile(12, TileColor.BLUE),
                new Tile(13, TileColor.BLUE)
        ));

        table = new Table(List.of(g1, g2, g3, r1, r2, r3));
    }

    @Test
    public void addTileTest1() {
        // normal add
        assertTrue(table.addTile(new Tile(7, TileColor.RED)));
        assertTrue(table.addTile(new Tile(2, TileColor.RED)));

        assertTrue(table.addTile(new Tile(7, TileColor.ORANGE)));
        assertTrue(table.addTile(new Tile(2, TileColor.ORANGE)));

        assertTrue(table.addTile(new Tile(10, TileColor.RED)));

        assertFalse(table.addTile(new Tile(13, TileColor.BLUE)));
    }

    @Test
    public void test2() {
        GroupMeld expect = new GroupMeld(
                List.of(
                        new Tile(3, TileColor.RED),
                        new Tile(3, TileColor.ORANGE),
                        new Tile(3, TileColor.BLACK)
                )
        );
        assertFalse(table.hasMeld(expect));
        assertTrue(table.addTile(new Tile(3, TileColor.BLACK)));
        assertTrue(table.hasMeld(expect));
    }

    @Test
    public void test3() {
        GroupMeld expect = new GroupMeld(
                List.of(
                        new Tile(3, TileColor.RED),
                        new Tile(3, TileColor.ORANGE),
                        new Tile(3, TileColor.BLUE)
                )
        );
        assertFalse(table.hasMeld(expect));
        assertTrue(table.addTile(new Tile(3, TileColor.BLUE)));
        assertTrue(table.hasMeld(expect));
    }

    @Test
    public void test4() {
        RunMeld expect1 = new RunMeld(
                List.of(
                        new Tile(4, TileColor.RED),
                        new Tile(5, TileColor.RED),
                        new Tile(6, TileColor.RED)
                )
        );

        GroupMeld expect2 = new GroupMeld(
                List.of(
                        new Tile(2, TileColor.ORANGE),
                        new Tile(2, TileColor.BLUE),
                        new Tile(2, TileColor.BLACK)
                )
        );

        RunMeld expect3 = new RunMeld(
                List.of(
                        new Tile(1, TileColor.RED),
                        new Tile(2, TileColor.RED),
                        new Tile(3, TileColor.RED)
                )
        );

        assertTrue(table.addTile(new Tile(1, TileColor.RED)));
        assertTrue(table.hasMeld(expect1));
        assertTrue(table.hasMeld(expect2));
        assertTrue(table.hasMeld(expect3));

    }


}
