package fr.efaya.game.todorpg.repository;

import fr.efaya.game.todorpg.domain.Loot;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author Sahbi Ktifa
 * created on 29/12/2017
 */
public interface LootRepository extends CrudRepository<Loot, String> {
    List<Loot> findAllByUsername(String username);
}
