package fr.efaya.game.todorpg.event.listener;

import fr.efaya.game.todorpg.ConstantUtils;
import fr.efaya.game.todorpg.domain.Dashboard;
import fr.efaya.game.todorpg.domain.User;
import fr.efaya.game.todorpg.domain.pojo.Notification;
import fr.efaya.game.todorpg.event.CompletedTaskEvent;
import fr.efaya.game.todorpg.event.CreatedTaskEvent;
import fr.efaya.game.todorpg.event.ValidatedTaskEvent;
import fr.efaya.game.todorpg.service.DashboardService;
import fr.efaya.game.todorpg.service.UserService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Sahbi Ktifa
 * created on 20/12/2017
 */
@Component
public class TaskListener {

    private UserService userService;
    private DashboardService dashboardService;

    public TaskListener(UserService userService, DashboardService dashboardService) {
        this.userService = userService;
        this.dashboardService = dashboardService;
    }

    @EventListener
    public void handleCreatedTaskEvent(CreatedTaskEvent event) {
        addNotification(event.getUser(), event.getMessage());
    }

    @EventListener
    public void handleCompletedTaskEvent(CompletedTaskEvent event) {
        addNotification(event.getUser(), event.getMessage());
    }

    @EventListener
    public void handleValidatedTaskEvent(ValidatedTaskEvent event) {
        User user = userService.retrieveUser(event.getTask().getAssignedTo());
        user.setCurrentExp(user.getCurrentExp() + event.getTask().getExp());
        user.getNotifications().add(new Notification(event.getMessage()));
        if (ConstantUtils.isLevelingUp(user.getLevel(), user.getCurrentExp())) {
            user.setLevel(user.getLevel() + 1);
            user.getNotifications().add(new Notification(String.format("Congratulations, you're leveling up! You are now level %s!", user.getLevel())));
        }
        userService.saveUser(user);
    }

    private void addNotification(User user, String message) {
        String username = user.getUsername();
        Dashboard dashboard = dashboardService.retrieveDashboardForUser(username);

        List<String> users = dashboard.getUsers().stream()
                .filter(u -> !u.equals(username))
                .collect(Collectors.toList());
        for (String _username : users) {
            User _user = userService.retrieveUser(_username);
            _user.getNotifications().add(new Notification(message));
            userService.saveUser(_user);
        }
    }
}
