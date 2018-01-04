package fr.efaya.game.dailyheroes.event;

import fr.efaya.game.dailyheroes.domain.Task;
import fr.efaya.game.dailyheroes.domain.User;

/**
 * @author Sahbi Ktifa
 * created on 02/01/2018
 */
public class CreatedUserEvent extends AbstractEvent {

    public CreatedUserEvent(Object source, Task task, User user) {
        super(source, task, user);
    }

}
