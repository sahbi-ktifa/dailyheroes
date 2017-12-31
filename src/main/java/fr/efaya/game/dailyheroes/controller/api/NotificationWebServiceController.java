package fr.efaya.game.dailyheroes.controller.api;

import fr.efaya.game.dailyheroes.domain.Notification;
import fr.efaya.game.dailyheroes.service.NotificationService;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

/**
 * @author Sahbi Ktifa
 * created on 27/12/2017
 */
@RestController
@RequestMapping("notification")
public class NotificationWebServiceController {

    private NotificationService notificationService;

    public NotificationWebServiceController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("{username}/check")
    public long checkNotifications(@PathVariable("username") String username) {
        List<Notification> notifications = notificationService.retrieveNotifications(username);
        if (!CollectionUtils.isEmpty(notifications)) {
            return notifications.stream()
                    .filter(n -> n.getRead() == 0)
                    .count();
        }
        return 0;
    }

    @GetMapping("{username}")
    public List<Notification> retrieveNotifications(@PathVariable("username") String username) {
        return notificationService.retrieveNotifications(username);
    }

    @PostMapping("{notificationId}")
    public void consumeNotification(@PathVariable("notificationId") String notificationId, Principal principal) {
        Notification notification = notificationService.retrieveNotification(notificationId);
        if (notification != null && principal.getName().equals(notification.getUsername())) {
            notificationService.consumeNotification(notificationId);
        }
    }
}
