package org.dnd.discord;

import java.util.HashMap;
import java.util.Map;

public final class Constants {
    final static int D4 = 4;
    final static int D6 = 6;
    final static int D8 = 8;
    final static int D10 = 10;
    final static int D12 = 12;
    final static int D20 = 20;
    final static int D100 = 100;

    public final static Map<Integer, Integer> statMap = new HashMap<>() {{
        put(3, -9);
        put(4, -6);
        put(5, -4);
        put(6, -2);
        put(7, -1);
        put(8, -0);
        put(9, 1);
        put(10, 2);
        put(11, 3);
        put(12, 4);
        put(13, 5);
        put(14, 7);
        put(15, 9);
        put(16, 12);
        put(17, 15);
        put(18, 19);
    }};
}
