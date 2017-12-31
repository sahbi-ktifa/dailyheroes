package fr.efaya.game.dailyheroes.service;

import fr.efaya.game.dailyheroes.domain.Notification;

import java.util.List;

/**
 * @author Sahbi Ktifa
 * created on 19/12/2017
 */
public interface NotificationService {
    Notification retrieveNotification(String notificationId);
    List<Notification> retrieveNotificationsUsingTask(String taskId);
    Notification saveNotification(Notification notification);
    List<Notification> retrieveNotifications(String username);
    void consumeNotification(String notificationId);
}
