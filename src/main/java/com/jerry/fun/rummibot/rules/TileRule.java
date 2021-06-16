package com.jerry.fun.rummibot.rules;

import java.util.Set;

public class TileRule {

    public final static int COLOR_BLACK = 1;
    public final static int COLOR_RED = 2;
    public final static int COLOR_BLUE = 3;
    public final static int COLOR_ORANGE = 4;

    public static Set<Integer> getColorCodes() {
        return Set.of(COLOR_BLACK, COLOR_RED, COLOR_BLUE, COLOR_ORANGE);
    }

    public static Set<Integer> getTileNumbers() {
        return Set.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13);
    }

}
