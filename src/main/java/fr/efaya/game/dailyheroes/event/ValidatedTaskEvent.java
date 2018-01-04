package fr.efaya.game.dailyheroes.event;

import fr.efaya.game.dailyheroes.domain.Task;
import fr.efaya.game.dailyheroes.domain.User;

/**
 * @author Sahbi Ktifa
 * created on 20/12/2017
 */
public class ValidatedTaskEvent extends AbstractEvent {

    public ValidatedTaskEvent(Object source, Task task, User user) {
        super(source, task, user);
    }
}
