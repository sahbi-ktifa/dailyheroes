package fr.efaya.game.dailyheroes.event.listener;

import fr.efaya.game.dailyheroes.domain.builder.NotificationBuilder;
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
            Notification notification = NotificationBuilder.newInstance()
                    .withMessage("has created a task, check if you can do it:")
                    .forUser(_username)
                    .withTask(event.getTask().getId())
                    .from(event.getUser().getUsername())
                    .withSuffix(event.getTask().getName())
                    .build();
            notificationService.saveNotification(notification);
        }
    }

    @EventListener
    public void handleCompletedTaskEvent(CompletedTaskEvent event) {
        List<String> users = addNotification(event.getUser());
        for (String _username : users) {
            Notification notification = NotificationBuilder.newInstance()
                    .withMessage("has completed a task, please review it:")
                    .forUser(_username)
                    .withTask(event.getTask().getId())
                    .requireValidation(true)
                    .from(event.getUser().getUsername())
                    .withSuffix(event.getTask().getName())
                    .build();
            notificationService.saveNotification(notification);
        }
    }

    @EventListener
    public void handleValidatedTaskEvent(ValidatedTaskEvent event) {
        User user = userService.retrieveUser(event.getTask().getAssignedTo());
        user.setCurrentExp(user.getCurrentExp() + event.getTask().getExp());
        Notification notification = NotificationBuilder.newInstance()
                .withMessage("has validated a task, well done:")
                .forUser(user.getUsername())
                .withTask(event.getTask().getId())
                .from(event.getUser().getUsername())
                .withSuffix(event.getTask().getName())
                .build();
        notificationService.saveNotification(notification);
        if (ConstantUtils.isLevelingUp(user.getLevel(), user.getCurrentExp())) {
            user.setLevel(user.getLevel() + 1);
            Notification _notification = NotificationBuilder.newInstance()
                    .withMessage("Congratulations, you are leveling up! You are now level")
                    .forUser(user.getUsername())
                    .withTask(event.getTask().getId())
                    .withSuffix(String.valueOf(user.getLevel()))
                    .build();
            notificationService.saveNotification(_notification);
            publisher.publishEvent(new LevelUpEvent(this, event.getTask(), user));
        }
        userService.saveUser(user);
    }

    @EventListener
    public void notifyOtherPlayersThatOneIsLevelingUp(LevelUpEvent event) {
        String username = event.getUser().getUsername();
        Dashboard dashboard = dashboardService.retrieveDashboardForUser(username);
        for (String user : dashboard.getUsers()) {
            if (!user.equals(username)) {
                Notification notification = NotificationBuilder.newInstance()
                        .withMessage("is now level")
                        .forUser(user)
                        .from(username)
                        .withSuffix(String.valueOf(event.getUser().getLevel()))
                        .build();

                notificationService.saveNotification(notification);
            }
        }

    }

    private List<String> addNotification(User user) {
        String username = user.getUsername();
        Dashboard dashboard = dashboardService.retrieveDashboardForUser(username);

        return dashboard.getUsers().stream()
                .filter(u -> !u.equals(username))
                .collect(Collectors.toList());
    }
}
