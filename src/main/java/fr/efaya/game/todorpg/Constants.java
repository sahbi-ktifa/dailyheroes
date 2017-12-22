package fr.efaya.game.todorpg;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Sahbi Ktifa
 * created on 19/12/2017
 */
public class Constants {

    static Map<Integer, Integer> expToLevels = new HashMap<Integer, Integer>() {{
        put(0, 1);
        put(100, 2);
        put(250, 3);
        put(475, 4);
        put(625, 5);
        put(1025, 6);
        put(1500, 7);
        put(2350, 8);
        put(3625, 9);
        put(4900, 10);
    }};

    public static Map<Integer, Integer> levelsToExp = expToLevels.entrySet()
            .stream()
            .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));

    public static Map<Integer, Integer> complexity = new HashMap<Integer, Integer>() {{
        put(1, 10);
        put(2, 20);
        put(3, 30);
        put(4, 60);
        put(5, 75);
    }};
}
