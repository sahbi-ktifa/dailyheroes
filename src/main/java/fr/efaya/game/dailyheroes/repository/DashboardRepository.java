package fr.efaya.game.dailyheroes.repository;

import fr.efaya.game.dailyheroes.domain.Dashboard;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author Sahbi Ktifa
 * created on 19/12/2017
 */
public interface DashboardRepository extends CrudRepository<Dashboard, String> {
    List<Dashboard> findAllByUsersContains(String user);
}
