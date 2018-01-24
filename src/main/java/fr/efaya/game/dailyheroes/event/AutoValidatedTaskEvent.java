package fr.efaya.game.dailyheroes.event;

import fr.efaya.game.dailyheroes.domain.Task;
import fr.efaya.game.dailyheroes.domain.User;

import java.util.List;

/**
 * @author Sahbi Ktifa
 * created on 20/12/2017
 */
public class AutoValidatedTaskEvent extends AbstractEvent {

    private List<User> users;

    public AutoValidatedTaskEvent(Object source, Task task, List<User> users) {
        super(source, task, null);
        this.users = users;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
