package fr.efaya.game.dailyheroes.repository;

import fr.efaya.game.dailyheroes.domain.Notification;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * @author Sahbi Ktifa
 * created on 19/12/2017
 */
public interface NotificationRepository extends PagingAndSortingRepository<Notification, String> {
    List<Notification> findAllByTaskId(String taskId);
    List<Notification> findAllByUsername(String username, Pageable pageable);
}
