package com.jerry.fun.rummibot.enums;

import com.jerry.fun.rummibot.rules.TileRule;

public enum TileColor {
    BLACK(TileRule.COLOR_BLACK),
    RED(TileRule.COLOR_RED),
    BLUE(TileRule.COLOR_BLUE),
    ORANGE(TileRule.COLOR_ORANGE);

    private int code;

    TileColor(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static TileColor valueOf(int colorCode) {
        switch (colorCode) {
            case TileRule.COLOR_BLACK:
                return TileColor.BLACK;
            case TileRule.COLOR_RED:
                return TileColor.RED;
            case TileRule.COLOR_BLUE:
                return TileColor.BLUE;
            case TileRule.COLOR_ORANGE:
                return TileColor.ORANGE;
            default:
                throw new IllegalArgumentException("Color code can only be 1,2,3, or 4");
        }
    }

}
