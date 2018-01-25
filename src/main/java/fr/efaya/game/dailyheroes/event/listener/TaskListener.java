package fr.efaya.game.dailyheroes.event.listener;

import fr.efaya.game.dailyheroes.controller.api.TaskWebServiceController;
import fr.efaya.game.dailyheroes.domain.Task;
import fr.efaya.game.dailyheroes.event.AbstractEvent;
import fr.efaya.game.dailyheroes.event.AutoValidatedTaskEvent;
import fr.efaya.game.dailyheroes.event.ValidatedTaskEvent;
import fr.efaya.game.dailyheroes.service.TaskService;
import fr.efaya.game.dailyheroes.service.UserService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * @author Sahbi Ktifa
 * created on 20/12/2017
 */
@Component
public class TaskListener {

    private TaskService taskService;
    private UserService userService;

    public TaskListener(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    @EventListener
    public void handleValidatedTaskEvent(ValidatedTaskEvent event) {
        repeatTask(event);
    }

    @EventListener
    public void handleAutoValidatedTaskEvent(AutoValidatedTaskEvent event) {
        repeatTask(event);
    }

    private void repeatTask(AbstractEvent event) {
        if (event.getTask().getDueDate() != null && event.getTask().getRedundancy() != null) {
            Task task = new Task();
            task.setRedundancy(event.getTask().getRedundancy());
            task.setName(event.getTask().getName());
            task.setNotes(event.getTask().getNotes());
            task.setStatus(Task.STATE.todo);
            task.setCategory(event.getTask().getCategory());
            task.setComplexity(event.getTask().getComplexity());
            task.setDashboardId(event.getTask().getDashboardId());
            task.setDueDate(generateDueDate(event.getTask().getRedundancy()));
            taskService.createTask(task, userService.retrieveUser(event.getTask().getAssignedTo()));
        }
    }

    private Date generateDueDate(TaskWebServiceController.TASK_REDUNDANCY redundancy) {
        LocalDateTime localDateTime = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        switch (redundancy) {
            case daily:
                localDateTime = localDateTime.plusDays(1);
                break;
            case weekly:
                localDateTime = localDateTime.plusDays(7);
                break;
            case monthly:
                localDateTime = localDateTime.plusMonths(1);
                break;
        }
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
