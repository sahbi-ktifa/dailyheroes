package fr.efaya.game.todorpg.service;

import fr.efaya.game.todorpg.domain.Item;
import fr.efaya.game.todorpg.domain.Loot;
import fr.efaya.game.todorpg.domain.User;
import fr.efaya.game.todorpg.repository.ItemRepository;
import fr.efaya.game.todorpg.repository.LootRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Sahbi Ktifa
 * created on 29/12/2017
 */
@Service
public class LootServiceImpl implements LootService {
    private LootRepository lootRepository;
    private ItemRepository itemRepository;

    public LootServiceImpl(LootRepository lootRepository, ItemRepository itemRepository) {
        this.lootRepository = lootRepository;
        this.itemRepository = itemRepository;
    }

    @Override
    public Item lootForLevel(User user) {
        List<Item> items = itemRepository.findAllByLevelCapLessThanEqualAndRarityGreaterThanEqualAndRepeatable(user.getLevel(), Math.toIntExact(Math.round(Math.random() * 100)), true);
        return doLoot(user, items);
    }

    @Override
    public Item lootForTask(User user) {
        List<Item> items = itemRepository.findAllByLevelCapLessThanEqualAndRarityGreaterThanEqualAndRepeatable(user.getLevel(), Math.toIntExact(Math.round(Math.random() * 100)), false);
        List<Loot> lootedItems = lootRepository.findAllByUsername(user.getUsername());
        items.removeIf(i -> lootedItems.stream().anyMatch(_i -> _i.getItemId().equals(i.getId())));
        return doLoot(user, items);
    }

    private Item doLoot(User user, List<Item> items) {
        if (!CollectionUtils.isEmpty(items)) {
            Item lootedItem = items.get(ThreadLocalRandom.current().nextInt(0, items.size()));
            Loot loot = new Loot();
            loot.setItemId(lootedItem.getId());
            loot.setUsername(user.getUsername());
            lootRepository.save(loot);
            return lootedItem;
        }
        return null;
    }
}
