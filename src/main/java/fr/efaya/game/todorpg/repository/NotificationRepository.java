package fr.efaya.game.todorpg.repository;

import fr.efaya.game.todorpg.domain.Notification;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author Sahbi Ktifa
 * created on 19/12/2017
 */
public interface NotificationRepository extends CrudRepository<Notification, String> {
    Notification findByTaskId(String taskId);
    List<Notification> findAllByUsername(String username);
}
