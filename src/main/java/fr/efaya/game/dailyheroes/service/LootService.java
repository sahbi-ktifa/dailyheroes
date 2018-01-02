package fr.efaya.game.dailyheroes.service;

import fr.efaya.game.dailyheroes.domain.Item;
import fr.efaya.game.dailyheroes.domain.Task;
import fr.efaya.game.dailyheroes.domain.User;

/**
 * @author Sahbi Ktifa
 * created on 29/12/2017
 */
public interface LootService {
    int lootBasicItems(User user);
    Item lootForLevel(User user);
    Item lootForTask(User user, Task task);
}
