package fr.efaya.game.dailyheroes.event;

import fr.efaya.game.dailyheroes.domain.Dashboard;
import fr.efaya.game.dailyheroes.domain.Task;
import fr.efaya.game.dailyheroes.domain.User;

/**
 * @author Sahbi Ktifa
 * created on 02/01/2018
 */
public class UserAddedInDashboard extends AbstractEvent {

    private Dashboard dashboard;

    public UserAddedInDashboard(Object source, Task task, User user, Dashboard dashboard) {
        super(source, task, user);
        this.dashboard = dashboard;
    }

    public Dashboard getDashboard() {
        return dashboard;
    }
}
