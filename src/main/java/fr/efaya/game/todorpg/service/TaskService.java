package fr.efaya.game.todorpg.service;

import fr.efaya.game.todorpg.domain.Task;
import fr.efaya.game.todorpg.domain.User;

import java.util.List;

/**
 * @author Sahbi Ktifa
 * created on 19/12/2017
 */
public interface TaskService {
    List<Task> retrieveTasks(String dashboardId);
    Task createTask(Task task, User user);
    Task updateTask(Task task);
    Task completeTask(Task task, User user);
    Task validTask(Task task, User user);
}
