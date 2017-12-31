package fr.efaya.game.dailyheroes;

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
        put(200, 3);
        put(310, 4);
        put(475, 5);
        put(625, 6);
        put(865, 7);
        put(1025, 8);
        put(1250, 9);
        put(1585, 10);
        put(2350, 11);
        put(3625, 12);
        put(4900, 13);
        put(6400, 14);
        put(8200, 15);
    }};

    public static Map<Integer, Integer> levelsToExp = expToLevels.entrySet()
            .stream()
            .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));

    public static Map<Integer, Integer> complexity = new HashMap<Integer, Integer>() {{
        put(0, 5);
        put(1, 10);
        put(2, 20);
        put(3, 30);
        put(4, 40);
        put(5, 45);
    }};
}
