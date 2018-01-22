package fr.efaya.game.dailyheroes.service;

import fr.efaya.game.dailyheroes.domain.Task;
import fr.efaya.game.dailyheroes.event.CompletedTaskEvent;
import fr.efaya.game.dailyheroes.event.CreatedTaskEvent;
import fr.efaya.game.dailyheroes.event.ValidatedTaskEvent;
import fr.efaya.game.dailyheroes.Constants;
import fr.efaya.game.dailyheroes.domain.User;
import fr.efaya.game.dailyheroes.repository.TaskRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author Sahbi Ktifa
 * created on 19/12/2017
 */
@Service
public class TaskServiceImpl implements TaskService {
    private TaskRepository repository;
    private UserService userService;
    private ApplicationEventPublisher publisher;

    public TaskServiceImpl(TaskRepository repository, UserService userService, ApplicationEventPublisher publisher) {
        this.repository = repository;
        this.userService = userService;
        this.publisher = publisher;
    }

    @Override
    public List<Task> retrieveTasks(String dashboardId, Task.STATE status) {
        return repository.findAllByDashboardIdAndStatus(dashboardId, status);
    }

    @Override
    public Task createTask(Task task, User user) {
        task.setId(UUID.randomUUID().toString());
        task.setCreationDate(new Date());
        Integer complexity = Constants.complexity.get(task.getComplexity()) - ((user.getLevel() - 1) * 4);
        task.setExp(calcExpPerComplexity(user, complexity));
        task = repository.save(task);
        publisher.publishEvent(new CreatedTaskEvent(this, task, user));
        return task;
    }

    @Override
    public Task updateTask(Task task, String username) {
        Task _task = repository.findOne(task.getId());
        _task.setNotes(task.getNotes());
        _task.setName(task.getName());
        _task.setDueDate(task.getDueDate());
        _task.setCategory(task.getCategory());
        _task.setRedundancy(task.getRedundancy());
        if (!task.getComplexity().equals(_task.getComplexity())) {
            _task.setComplexity(task.getComplexity());
            _task.setExp(calcExpPerComplexity(userService.retrieveUser(username), task.getComplexity()));
        }
        return repository.save(_task);
    }

    @Override
    public Task completeTask(String taskId, User user) {
        Task task = repository.findOne(taskId);
        if (task.getStatus().equals(Task.STATE.todo)) {
            task.setStatus(Task.STATE.tovalidate);
            task.setAssignedTo(user.getUsername());
            task = repository.save(task);
            publisher.publishEvent(new CompletedTaskEvent(this, task, user));
        }
        return task;
    }

    @Override
    public Task validTask(String taskId, User user) {
        Task task = repository.findOne(taskId);
        if (task.getStatus().equals(Task.STATE.tovalidate)) {
            task.setStatus(Task.STATE.done);
            task = repository.save(task);
            publisher.publishEvent(new ValidatedTaskEvent(this, task, user));
        }
        return task;
    }

    @Override
    public Task deleteTask(String taskId) {
        Task task = repository.findOne(taskId);
        repository.delete(taskId);
        return task;
    }

    private Integer calcExpPerComplexity(User user, Integer complexity) {
        return (complexity * Constants.levelsToExp.get(user.getLevel() + 1)) / 100;
    }
}
