package fr.efaya.game.dailyheroes.service;

import fr.efaya.game.dailyheroes.Constants;
import fr.efaya.game.dailyheroes.domain.Item;
import fr.efaya.game.dailyheroes.domain.Loot;
import fr.efaya.game.dailyheroes.domain.Task;
import fr.efaya.game.dailyheroes.domain.User;
import fr.efaya.game.dailyheroes.repository.ItemRepository;
import fr.efaya.game.dailyheroes.repository.LootRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
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
    public List<Loot> retrieveLoots(String username) {
        return lootRepository.findAllByUsername(username);
    }

    @Override
    public int lootBasicItems(User user) {
        List<Item> basicItems = itemRepository.findAllByLevelCapLessThanEqualAndRarityGreaterThanEqualAndRepeatable(0, 0, false);
        removeAlreadyLootedItems(user, basicItems);

        for (Item basicItem : basicItems) {
            Loot loot = new Loot();
            loot.setRewardDate(new Date());
            loot.setItemId(basicItem.getId());
            loot.setUsername(user.getUsername());
            lootRepository.save(loot);
        }
        return basicItems.size();
    }

    @Override
    public Item lootForLevel(User user) {
        List<Item> items = itemRepository.findAllByLevelCapLessThanEqualAndRarityGreaterThanEqualAndRepeatable(user.getLevel(), Math.toIntExact(Math.round(Math.random() * 100)), true);
        return doLoot(user, items);
    }

    @Override
    public Item lootForTask(User user, Task task) {
        int dropRate = Constants.complexity.get(task.getComplexity()) * 2;
        if (Math.random() * 100 <= dropRate) {
            List<Item> items = itemRepository.findAllByLevelCapLessThanEqualAndRarityGreaterThanEqualAndRepeatable(user.getLevel(), Math.toIntExact(Math.round(Math.random() * 100)), false);
            removeAlreadyLootedItems(user, items);
            return doLoot(user, items);
        }
        return null;
    }

    @Override
    public void receiveLoot(String lootId) {
        Loot loot = lootRepository.findOne(lootId);
        if (loot != null && !Boolean.TRUE.equals(loot.getReceived())) {
            loot.setReceived(true);
            loot.setReceivedDate(new Date());
            lootRepository.save(loot);
        }
    }

    private Item doLoot(User user, List<Item> items) {
        if (!CollectionUtils.isEmpty(items)) {
            Item lootedItem = items.get(ThreadLocalRandom.current().nextInt(0, items.size()));
            Loot loot = new Loot();
            loot.setRewardDate(new Date());
            loot.setItemId(lootedItem.getId());
            loot.setUsername(user.getUsername());
            lootRepository.save(loot);
            return lootedItem;
        }
        return null;
    }

    private void removeAlreadyLootedItems(User user, List<Item> basicItems) {
        List<Loot> lootedItems = lootRepository.findAllByUsername(user.getUsername());
        basicItems.removeIf(i -> lootedItems.stream().anyMatch(_i -> _i.getItemId().equals(i.getId())));
    }
}
