package fr.efaya.game.dailyheroes.domain.pojo;

import java.util.List;

/**
 * @author Sahbi Ktifa
 * created on 21/12/2017
 */
public class RegisterCommand {
    private List<String> playerNames;
    private String password;
    private String dashboardName;

    public List<String> getPlayerNames() {
        return playerNames;
    }

    public void setPlayerNames(List<String> playerNames) {
        this.playerNames = playerNames;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDashboardName() {
        return dashboardName;
    }

    public void setDashboardName(String dashboardName) {
        this.dashboardName = dashboardName;
    }
}
