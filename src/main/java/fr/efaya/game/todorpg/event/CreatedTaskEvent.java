package fr.efaya.game.todorpg.event;

import fr.efaya.game.todorpg.domain.Task;
import fr.efaya.game.todorpg.domain.User;

/**
 * @author Sahbi Ktifa
 * created on 20/12/2017
 */
public class CreatedTaskEvent extends SimpleTaskEvent {

    public CreatedTaskEvent(Object source, Task task, User user) {
        super(source, task, user);
    }

    @Override
    public String getMessage() {
        return String.format("%s has created a task named: '%s', check if you can do it!", getUser().getUsername(), getTask().getName());
    }
}
