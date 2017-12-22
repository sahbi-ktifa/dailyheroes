package fr.efaya.game.todorpg;

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
}
