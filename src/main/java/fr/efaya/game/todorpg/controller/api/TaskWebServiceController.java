package fr.efaya.game.todorpg.controller.api;

import fr.efaya.game.todorpg.domain.Task;
import fr.efaya.game.todorpg.service.TaskService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Sahbi Ktifa
 * created on 24/12/2017
 */
@RestController
@RequestMapping("task")
public class TaskWebServiceController {

    private TaskService taskService;

    public TaskWebServiceController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("{dashboardId}")
    public List<Task> retrieveTasksForDashboard(@PathVariable("dashboardId") String dashboardId) {
        return taskService.retrieveTasks(dashboardId);
    }
}
