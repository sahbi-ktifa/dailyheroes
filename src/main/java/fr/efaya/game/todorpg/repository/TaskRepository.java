package fr.efaya.game.todorpg.repository;

import fr.efaya.game.todorpg.domain.Task;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author Sahbi Ktifa
 * created on 19/12/2017
 */
public interface TaskRepository extends CrudRepository<Task, String> {

    List<Task> findAllByDashboardId(String dashboardId);
}
