package fr.efaya.game.dailyheroes.service;

import fr.efaya.game.dailyheroes.domain.Task;
import fr.efaya.game.dailyheroes.domain.User;

import java.util.List;

/**
 * @author Sahbi Ktifa
 * created on 19/12/2017
 */
public interface TaskService {
    List<Task> retrieveTasks(String dashboardId, Task.STATE status);
    Task createTask(Task task, User user);
    Task updateTask(Task task);
    Task completeTask(String taskId, User user);
    Task validTask(String taskId, User user);
    Task deleteTask(String taskId);
}
