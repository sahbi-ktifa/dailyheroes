package fr.efaya.game.dailyheroes;

/**
 * @author Sahbi Ktifa
 * created on 20/12/2017
 */
public class ConstantUtils {
    public static boolean isLevelingUp(Integer currentLevel, Integer exp) {
        for (Integer key : Constants.expToLevels.keySet()) {
            if (exp > key && Constants.expToLevels.get(key) > currentLevel) {
                return true;
            }
        }
        return false;
    }

    public static Integer calcExpPerComplexity(Integer level, Integer complexity) {
        return (Constants.complexity.get(complexity) * (Constants.levelsToExp.get(level + 1) - Constants.levelsToExp.get(level))) / 100;
    }
}
