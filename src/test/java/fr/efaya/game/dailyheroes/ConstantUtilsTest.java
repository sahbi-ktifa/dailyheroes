package fr.efaya.game.dailyheroes;

import org.hamcrest.core.IsEqual;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Sahbi Ktifa
 * created on 23/01/2018
 */
public class ConstantUtilsTest {

    @Test
    public void checkExpGiven_Level1() {
        Assert.assertThat(ConstantUtils.calcExpPerComplexity(1, 0), IsEqual.equalTo(5));
        Assert.assertThat(ConstantUtils.calcExpPerComplexity(1, 1), IsEqual.equalTo(10));
        Assert.assertThat(ConstantUtils.calcExpPerComplexity(1, 2), IsEqual.equalTo(20));
        Assert.assertThat(ConstantUtils.calcExpPerComplexity(1, 3), IsEqual.equalTo(30));
        Assert.assertThat(ConstantUtils.calcExpPerComplexity(1, 4), IsEqual.equalTo(40));
        Assert.assertThat(ConstantUtils.calcExpPerComplexity(1, 5), IsEqual.equalTo(45));
    }

    @Test
    public void checkExpGiven_Level2() {
        Assert.assertThat(ConstantUtils.calcExpPerComplexity(2, 0), IsEqual.equalTo(5));
        Assert.assertThat(ConstantUtils.calcExpPerComplexity(2, 1), IsEqual.equalTo(10));
        Assert.assertThat(ConstantUtils.calcExpPerComplexity(2, 2), IsEqual.equalTo(20));
        Assert.assertThat(ConstantUtils.calcExpPerComplexity(2, 3), IsEqual.equalTo(30));
        Assert.assertThat(ConstantUtils.calcExpPerComplexity(2, 4), IsEqual.equalTo(40));
        Assert.assertThat(ConstantUtils.calcExpPerComplexity(2, 5), IsEqual.equalTo(45));
    }

    @Test
    public void checkExpGiven_Level3() {
        Assert.assertThat(ConstantUtils.calcExpPerComplexity(3, 0), IsEqual.equalTo(5));
        Assert.assertThat(ConstantUtils.calcExpPerComplexity(3, 1), IsEqual.equalTo(11));
        Assert.assertThat(ConstantUtils.calcExpPerComplexity(3, 2), IsEqual.equalTo(22));
        Assert.assertThat(ConstantUtils.calcExpPerComplexity(3, 3), IsEqual.equalTo(33));
        Assert.assertThat(ConstantUtils.calcExpPerComplexity(3, 4), IsEqual.equalTo(44));
        Assert.assertThat(ConstantUtils.calcExpPerComplexity(3, 5), IsEqual.equalTo(49));
    }
}