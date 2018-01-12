package fr.efaya.game.dailyheroes.event.listener;

import fr.efaya.game.dailyheroes.domain.Notification;
import fr.efaya.game.dailyheroes.domain.builder.NotificationBuilder;
import fr.efaya.game.dailyheroes.event.UserAddedInDashboard;
import fr.efaya.game.dailyheroes.service.NotificationService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author Sahbi Ktifa
 * created on 04/01/2018
 */
@Component
public class DashboardListener {

    private NotificationService notificationService;

    public DashboardListener(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @EventListener
    public void dashboardCreated(UserAddedInDashboard event) {
        Notification notification = NotificationBuilder.newInstance()
                .forUser(event.getUser().getUsername())
                .withMessage("You were added in a new game, join it now:")
                .withSuffix(event.getDashboard().getName())
                .requireValidation(true)
                .addExtra("dashboardId", event.getDashboard().getId())
                .addExtra("icon", "fa-sign-in")
                .build();
        notificationService.saveNotification(notification);
    }
}
