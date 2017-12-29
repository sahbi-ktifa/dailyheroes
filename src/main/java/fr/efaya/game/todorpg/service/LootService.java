package fr.efaya.game.todorpg.service;

import fr.efaya.game.todorpg.domain.Item;
import fr.efaya.game.todorpg.domain.User;

/**
 * @author Sahbi Ktifa
 * created on 29/12/2017
 */
public interface LootService {
    Item lootForLevel(User user);
    Item lootForTask(User user);
}
