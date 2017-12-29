package fr.efaya.game.todorpg.event.listener;

import fr.efaya.game.todorpg.domain.Item;
import fr.efaya.game.todorpg.domain.Notification;
import fr.efaya.game.todorpg.event.LevelUpEvent;
import fr.efaya.game.todorpg.event.ValidatedTaskEvent;
import fr.efaya.game.todorpg.service.LootService;
import fr.efaya.game.todorpg.service.NotificationService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author Sahbi Ktifa
 * created on 29/12/2017
 */
@Component
public class LootListener {

    private LootService lootService;
    private NotificationService notificationService;

    public LootListener(LootService lootService, NotificationService notificationService) {
        this.lootService = lootService;
        this.notificationService = notificationService;
    }

    @EventListener
    public void handleLevelUpEvent(LevelUpEvent event) {
        Item item = lootService.lootForLevel(event.getUser());
        if (item != null) {
            notificationService.saveNotification(new Notification(String.format("Congratulations, you've been awarded with: '%s'.",
                    item.getName()), event.getUser().getUsername(), event.getTask().getId()));
        }
    }

    @EventListener
    public void handleValidatedTaskEvent(ValidatedTaskEvent event) {
        Item item = lootService.lootForTask(event.getUser());
        if (item != null) {
            notificationService.saveNotification(new Notification(String.format("Congratulations, you've been awarded with: '%s'.",
                    item.getName()), event.getUser().getUsername(), event.getTask().getId()));
        }
    }
}
