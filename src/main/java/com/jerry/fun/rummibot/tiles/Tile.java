package com.jerry.fun.rummibot.tiles;

import com.jerry.fun.rummibot.enums.TileColor;
import com.jerry.fun.rummibot.rules.TileRule;

import java.util.Objects;

public class Tile {

    private Integer number;
    private TileColor tileColor;

    public Tile(Integer number, TileColor tileColor) {

        if (!TileRule.getTileNumbers().contains(number)) {
            throw new IllegalArgumentException("Tile Number cannot be greater than 13 or less than 1.");
        }
        if (!TileRule.getColorCodes().contains(tileColor.getCode())) {
            throw new IllegalArgumentException("Color code can only be 1,2,3, or 4");
        }

        this.number = number;
        this.tileColor = tileColor;
    }

    public Tile(Integer number, int colorCode) {
        this(number, TileColor.valueOf(colorCode));
    }

    public Integer getNumber() {
        return number;
    }

    public TileColor getColor() {
        return tileColor;
    }

    @Override
    public int hashCode() {
        return tileColor.getCode() * number + 117;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tile tile = (Tile) o;
        return Objects.equals(number, tile.number) && tileColor == tile.tileColor;
    }
}
