package fr.efaya.game.dailyheroes.event;

import fr.efaya.game.dailyheroes.domain.Task;
import fr.efaya.game.dailyheroes.domain.User;
import org.springframework.context.ApplicationEvent;

/**
 * @author Sahbi Ktifa
 * created on 20/12/2017
 */
public abstract class AbstractEvent extends ApplicationEvent {

    private Task task;
    private User user;

    AbstractEvent(Object source, Task task, User user) {
        super(source);
        this.task = task;
        this.user = user;
    }

    public Task getTask() {
        return task;
    }

    public User getUser() {
        return user;
    }
}
