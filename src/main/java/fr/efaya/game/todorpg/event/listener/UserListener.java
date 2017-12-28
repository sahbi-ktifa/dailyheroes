package fr.efaya.game.todorpg.event.listener;

import fr.efaya.game.todorpg.ConstantUtils;
import fr.efaya.game.todorpg.domain.Dashboard;
import fr.efaya.game.todorpg.domain.Notification;
import fr.efaya.game.todorpg.domain.User;
import fr.efaya.game.todorpg.event.CompletedTaskEvent;
import fr.efaya.game.todorpg.event.CreatedTaskEvent;
import fr.efaya.game.todorpg.event.ValidatedTaskEvent;
import fr.efaya.game.todorpg.service.DashboardService;
import fr.efaya.game.todorpg.service.NotificationService;
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
public class UserListener {

    private UserService userService;
    private DashboardService dashboardService;
    private NotificationService notificationService;

    public UserListener(UserService userService, DashboardService dashboardService, NotificationService notificationService) {
        this.userService = userService;
        this.dashboardService = dashboardService;
        this.notificationService = notificationService;
    }

    @EventListener
    public void handleCreatedTaskEvent(CreatedTaskEvent event) {
        List<String> users = addNotification(event.getUser());
        for (String _username : users) {
            notificationService.saveNotification(new Notification(event.getMessage(), _username, event.getTask().getId()));
        }
    }

    @EventListener
    public void handleCompletedTaskEvent(CompletedTaskEvent event) {
        List<String> users = addNotification(event.getUser());
        for (String _username : users) {
            notificationService.saveNotification(new Notification(event.getMessage(), _username, event.getTask().getId(), true));
        }
    }

    @EventListener
    public void handleValidatedTaskEvent(ValidatedTaskEvent event) {
        User user = userService.retrieveUser(event.getTask().getAssignedTo());
        user.setCurrentExp(user.getCurrentExp() + event.getTask().getExp());
        notificationService.saveNotification(new Notification(event.getMessage(), user.getUsername(), event.getTask().getId()));
        if (ConstantUtils.isLevelingUp(user.getLevel(), user.getCurrentExp())) {
            user.setLevel(user.getLevel() + 1);
            notificationService.saveNotification(new Notification(String.format("Congratulations, you're leveling up! You are now level %s!", user.getLevel()), user.getUsername(), event.getTask().getId()));
        }
        userService.saveUser(user);
    }

    private List<String> addNotification(User user) {
        String username = user.getUsername();
        Dashboard dashboard = dashboardService.retrieveDashboardForUser(username);

        return dashboard.getUsers().stream()
                .filter(u -> !u.equals(username))
                .collect(Collectors.toList());
    }
}
