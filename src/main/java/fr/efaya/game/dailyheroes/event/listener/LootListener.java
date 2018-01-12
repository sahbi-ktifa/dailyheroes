package fr.efaya.game.dailyheroes.event.listener;

import fr.efaya.game.dailyheroes.domain.Item;
import fr.efaya.game.dailyheroes.domain.Notification;
import fr.efaya.game.dailyheroes.domain.User;
import fr.efaya.game.dailyheroes.domain.builder.NotificationBuilder;
import fr.efaya.game.dailyheroes.event.CreatedUserEvent;
import fr.efaya.game.dailyheroes.event.LevelUpEvent;
import fr.efaya.game.dailyheroes.event.ValidatedTaskEvent;
import fr.efaya.game.dailyheroes.service.LootService;
import fr.efaya.game.dailyheroes.service.NotificationService;
import fr.efaya.game.dailyheroes.service.UserService;
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
    private UserService userService;

    public LootListener(LootService lootService, NotificationService notificationService, UserService userService) {
        this.lootService = lootService;
        this.notificationService = notificationService;
        this.userService = userService;
    }

    @EventListener
    public void handleLevelUpEvent(LevelUpEvent event) {
        Item item = lootService.lootForLevel(event.getUser());
        if (item != null) {
            Notification notification = NotificationBuilder.newInstance()
                    .withMessage("Congratulations, you have been awarded with:")
                    .withSuffix(item.getName())
                    .forUser(event.getUser().getUsername())
                    .withTask(event.getTask().getId())
                    .addExtra("icon", "fa-gift")
                    .build();
            notificationService.saveNotification(notification);
        }
    }

    @EventListener
    public void handleValidatedTaskEvent(ValidatedTaskEvent event) {
        User user = userService.retrieveUser(event.getTask().getAssignedTo());
        if (user != null) {
            Item item = lootService.lootForTask(user, event.getTask());
            if (item != null) {
                Notification notification = NotificationBuilder.newInstance()
                        .withMessage("Congratulations, you have been awarded with:")
                        .withSuffix(item.getName())
                        .forUser(user.getUsername())
                        .addExtra("icon", "fa-gift")
                        .build();
                notificationService.saveNotification(notification);
            }
        }
    }

    @EventListener
    public void lootBasicItems(CreatedUserEvent event) {
        int count = lootService.lootBasicItems(event.getUser());
        if (count > 0) {
            Notification notification = NotificationBuilder.newInstance()
                    .withMessage("New items have been unlocked, check your profile!")
                    .forUser(event.getUser().getUsername())
                    .addExtra("icon", "fa-gift")
                    .build();
            notificationService.saveNotification(notification);
        }
    }
}
