package fr.efaya.game.todorpg.repository;

import fr.efaya.game.todorpg.domain.Item;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author Sahbi Ktifa
 * created on 29/12/2017
 */
public interface ItemRepository extends CrudRepository<Item, String> {
    List<Item> findAllByLevelCapLessThanEqualAndRarityGreaterThanEqualAndRepeatable(Integer levelCap, Integer rarity, boolean repeatable);
}
