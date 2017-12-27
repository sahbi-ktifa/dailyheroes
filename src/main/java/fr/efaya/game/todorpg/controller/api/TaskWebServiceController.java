package fr.efaya.game.todorpg.controller.api;

import fr.efaya.game.todorpg.domain.Task;
import fr.efaya.game.todorpg.service.TaskService;
import fr.efaya.game.todorpg.service.UserService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

/**
 * @author Sahbi Ktifa
 * created on 24/12/2017
 */
@RestController
@RequestMapping("task")
public class TaskWebServiceController {

    private TaskService taskService;
    private UserService userService;

    public enum TASK_TYPE {administrative, fun, DIY, cleaning}

    public TaskWebServiceController(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    @GetMapping("info/categories")
    public TASK_TYPE[] retrieveTaskCategories() {
        return TASK_TYPE.values();
    }

    @GetMapping("{dashboardId}")
    public List<Task> retrieveTasksForDashboard(@PathVariable("dashboardId") String dashboardId) {
        return taskService.retrieveTasks(dashboardId, Task.STATE.todo);
    }

    @PostMapping
    public Task createTask(@RequestBody @Valid Task task, Principal principal) {
        return taskService.createTask(task, userService.retrieveUser(principal.getName()));
    }

    @PostMapping("{taskId}")
    public Task updateTask(@PathVariable("taskId") String taskId, @RequestBody @Valid Task task) {
        task.setId(taskId);
        return taskService.updateTask(task);
    }

    @PostMapping("{taskId}/valid")
    public Task validTask(@PathVariable("taskId") String taskId, Principal principal) {
        return taskService.completeTask(taskId, userService.retrieveUser(principal.getName()));
    }

    @DeleteMapping("{taskId}")
    public Task deleteTask(@PathVariable("taskId") String taskId) {
        return taskService.deleteTask(taskId);
    }
}
