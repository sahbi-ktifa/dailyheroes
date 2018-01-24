package fr.efaya.game.dailyheroes.event.listener;

import fr.efaya.game.dailyheroes.ConstantUtils;
import fr.efaya.game.dailyheroes.domain.Dashboard;
import fr.efaya.game.dailyheroes.domain.Notification;
import fr.efaya.game.dailyheroes.domain.User;
import fr.efaya.game.dailyheroes.domain.builder.NotificationBuilder;
import fr.efaya.game.dailyheroes.event.AbstractEvent;
import fr.efaya.game.dailyheroes.event.AutoValidatedTaskEvent;
import fr.efaya.game.dailyheroes.event.CompletedTaskEvent;
import fr.efaya.game.dailyheroes.event.CreatedTaskEvent;
import fr.efaya.game.dailyheroes.event.LevelUpEvent;
import fr.efaya.game.dailyheroes.event.ValidatedTaskEvent;
import fr.efaya.game.dailyheroes.service.DashboardService;
import fr.efaya.game.dailyheroes.service.NotificationService;
import fr.efaya.game.dailyheroes.service.UserService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Collections;
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
        List<String> users = retrieveUsersInDashboard(event.getUser(), event.getTask().getDashboardId());
        for (String _username : users) {
            if (!_username.equals(event.getUser().getUsername())) {
                Notification notification = NotificationBuilder.newInstance()
                        .withMessage("has created a task, check if you can do it:")
                        .forUser(_username)
                        .withTask(event.getTask().getId())
                        .from(event.getUser().getUsername())
                        .withSuffix(event.getTask().getName())
                        .addExtra("icon", "fa-plus-circle")
                        .build();
                notificationService.saveNotification(notification);
            }
        }
    }

    @EventListener
    public void handleCompletedTaskEvent(CompletedTaskEvent event) {
        List<String> users = retrieveUsersInDashboard(event.getUsers().get(0), event.getTask().getDashboardId());
        List<String> achievers = event.getUsers().stream().map(User::getUsername).collect(Collectors.toList());
        if (users.stream().allMatch(achievers::contains)) {
            publisher.publishEvent(new AutoValidatedTaskEvent(this, event.getTask(), event.getUsers()));
        } else {
            for (String _username : users) {
                if (event.getUsers().stream().noneMatch(u -> u.getUsername().equals(_username))) {
                    Notification notification = NotificationBuilder.newInstance()
                            .withMessage(event.getUsers().size() > 1 ? "have completed a task, please review it:" : "has completed a task, please review it:")
                            .forUser(_username)
                            .withTask(event.getTask().getId())
                            .requireValidation(true)
                            .from(event.getUsers().stream().map(User::getUsername).collect(Collectors.joining(",")))
                            .withSuffix(event.getTask().getName())
                            .addExtra("icon", "fa-check-circle-o")
                            .build();
                    notificationService.saveNotification(notification);
                }
            }
        }
    }

    @EventListener
    public void handleValidatedTaskEvent(ValidatedTaskEvent event) {
        User user = userService.retrieveUser(event.getTask().getAssignedTo());
        Notification notification = NotificationBuilder.newInstance()
                .withMessage("has validated a task, well done:")
                .forUser(user.getUsername())
                .withTask(event.getTask().getId())
                .from(event.getUser().getUsername())
                .withSuffix(event.getTask().getName())
                .addExtra("icon", "fa-check-circle-o")
                .build();
        notificationService.saveNotification(notification);
        checkForLevelUp(event, user);
    }

    @EventListener
    public void handleAutoValidatedTaskEvent(AutoValidatedTaskEvent event) {
        for (User user : event.getUsers()) {
            checkForLevelUp(event, user);
        }
    }

    private void checkForLevelUp(AbstractEvent event, User user) {
        user.setCurrentExp(user.getCurrentExp() + ConstantUtils.calcExpPerComplexity(user.getLevel(), event.getTask().getComplexity()));
        if (ConstantUtils.isLevelingUp(user.getLevel(), user.getCurrentExp())) {
            user.setLevel(user.getLevel() + 1);
            Notification _notification = NotificationBuilder.newInstance()
                    .withMessage("Congratulations, you are leveling up! You are now level")
                    .forUser(user.getUsername())
                    .withTask(event.getTask().getId())
                    .withSuffix(String.valueOf(user.getLevel()))
                    .addExtra("icon", "fa-level-up")
                    .build();
            notificationService.saveNotification(_notification);
            publisher.publishEvent(new LevelUpEvent(this, event.getTask(), user));
        }
        userService.saveUser(user);
    }

    @EventListener
    public void notifyOtherPlayersThatOneIsLevelingUp(LevelUpEvent event) {
        String username = event.getUser().getUsername();
        List<Dashboard> dashboards = dashboardService.retrieveDashboardsForUser(username);
        for (Dashboard dashboard : dashboards) {
            for (String user : dashboard.getUsers()) {
                if (!user.equals(username)) {
                    Notification notification = NotificationBuilder.newInstance()
                            .withMessage("is now level")
                            .forUser(user)
                            .from(username)
                            .withSuffix(String.valueOf(event.getUser().getLevel()))
                            .addExtra("icon", "fa-level-up")
                            .build();

                    notificationService.saveNotification(notification);
                }
            }
        }

    }

    private List<String> retrieveUsersInDashboard(User user, String dashboardId) {
        String username = user.getUsername();
        List<Dashboard> dashboards = dashboardService.retrieveDashboardsForUser(username);

        Dashboard dashboard = dashboards.stream()
                .filter(d -> d.getId().equals(dashboardId))
                .findFirst()
                .orElse(null);
        if (dashboard != null) {
            return dashboard.getUsers();
        } else {
            return Collections.emptyList();
        }
    }
}
