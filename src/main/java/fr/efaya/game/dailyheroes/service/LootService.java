package fr.efaya.game.dailyheroes.service;

import fr.efaya.game.dailyheroes.domain.Item;
import fr.efaya.game.dailyheroes.domain.User;

/**
 * @author Sahbi Ktifa
 * created on 29/12/2017
 */
public interface LootService {
    Item lootForLevel(User user);
    Item lootForTask(User user);
}
