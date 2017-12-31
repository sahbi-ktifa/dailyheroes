package fr.efaya.game.dailyheroes.service;

import fr.efaya.game.dailyheroes.domain.Notification;
import fr.efaya.game.dailyheroes.repository.NotificationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Sahbi Ktifa
 * created on 27/12/2017
 */
@Service
public class NotificationServiceImpl implements NotificationService {

    private NotificationRepository repository;

    public NotificationServiceImpl(NotificationRepository repository) {
        this.repository = repository;
    }

    @Override
    public Notification retrieveNotification(String notificationId) {
        return repository.findOne(notificationId);
    }

    @Override
    public List<Notification> retrieveNotificationsUsingTask(String taskId) {
        return repository.findAllByTaskId(taskId);
    }

    @Override
    public Notification saveNotification(Notification notification) {
        return repository.save(notification);
    }

    @Override
    public List<Notification> retrieveNotifications(String username) {
        return repository.findAllByUsername(username);
    }

    @Override
    public void consumeNotification(String notificationId) {
        Notification notification = repository.findOne(notificationId);
        notification.setRead(1);
        repository.save(notification);
    }
}
