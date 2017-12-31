package fr.efaya.game.dailyheroes.event;

import fr.efaya.game.dailyheroes.domain.Task;
import fr.efaya.game.dailyheroes.domain.User;

/**
 * @author Sahbi Ktifa
 * created on 20/12/2017
 */
public class CompletedTaskEvent extends AbstractEvent {

    public CompletedTaskEvent(Object source, Task task, User user) {
        super(source, task, user);
    }

    @Override
    public String getMessage() {
        return String.format("%s has completed task: '%s', please review it.", getUser().getUsername(), getTask().getName());
    }
}