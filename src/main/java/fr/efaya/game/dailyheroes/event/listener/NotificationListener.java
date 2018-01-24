package fr.efaya.game.dailyheroes.event.listener;

import fr.efaya.game.dailyheroes.domain.Notification;
import fr.efaya.game.dailyheroes.event.AbstractEvent;
import fr.efaya.game.dailyheroes.event.AutoValidatedTaskEvent;
import fr.efaya.game.dailyheroes.event.ValidatedTaskEvent;
import fr.efaya.game.dailyheroes.service.NotificationService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author Sahbi Ktifa
 * created on 20/12/2017
 */
@Component
public class NotificationListener {

    private NotificationService notificationService;

    public NotificationListener(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @EventListener
    public void handleValidatedTaskEvent(ValidatedTaskEvent event) {
        cleanNotification(event);
    }

    @EventListener
    public void handleAutoValidatedTaskEvent(AutoValidatedTaskEvent event) {
        cleanNotification(event);
    }

    private void cleanNotification(AbstractEvent event) {
        List<Notification> notifications = notificationService.retrieveNotificationsUsingTask(event.getTask().getId());
        if (!CollectionUtils.isEmpty(notifications)) {
            notifications.forEach(notification -> {
                notification.setValidated(true);
                notification.setRead(1);
                notificationService.saveNotification(notification);
            });
        }
    }
}
