package fr.efaya.game.dailyheroes.event.listener;

import fr.efaya.game.dailyheroes.event.CompletedTaskEvent;
import fr.efaya.game.dailyheroes.ConstantUtils;
import fr.efaya.game.dailyheroes.domain.Dashboard;
import fr.efaya.game.dailyheroes.domain.Notification;
import fr.efaya.game.dailyheroes.domain.User;
import fr.efaya.game.dailyheroes.event.CreatedTaskEvent;
import fr.efaya.game.dailyheroes.event.LevelUpEvent;
import fr.efaya.game.dailyheroes.event.ValidatedTaskEvent;
import fr.efaya.game.dailyheroes.service.DashboardService;
import fr.efaya.game.dailyheroes.service.NotificationService;
import fr.efaya.game.dailyheroes.service.UserService;
import org.springframework.context.ApplicationEventPublisher;
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
    private ApplicationEventPublisher publisher;

    public UserListener(UserService userService, DashboardService dashboardService, NotificationService notificationService, ApplicationEventPublisher publisher) {
        this.userService = userService;
        this.dashboardService = dashboardService;
        this.notificationService = notificationService;
        this.publisher = publisher;
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
            publisher.publishEvent(new LevelUpEvent(this, event.getTask(), user));
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
