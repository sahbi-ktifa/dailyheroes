package fr.efaya.game.dailyheroes.controller.api;

import fr.efaya.game.dailyheroes.Constants;
import fr.efaya.game.dailyheroes.domain.Loot;
import fr.efaya.game.dailyheroes.domain.User;
import fr.efaya.game.dailyheroes.service.LootService;
import fr.efaya.game.dailyheroes.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author Sahbi Ktifa
 * created on 24/12/2017
 */
@RestController
@RequestMapping("user")
public class UserWebServiceController {

    private UserService userService;
    private LootService lootService;

    public UserWebServiceController(UserService userService, LootService lootService) {
        this.userService = userService;
        this.lootService = lootService;
    }

    @GetMapping("{username}")
    public User retrieveUser(@PathVariable("username") String username) {
        return userService.retrieveUser(username);
    }

    @GetMapping("{username}/{userLevel}")
    public Integer retrieveUserNextLevelCap(@PathVariable("username") String username, @PathVariable("userLevel") Integer userLevel) {
        return Constants.levelsToExp.get(userLevel + 1);
    }

    @PostMapping("{username}/avatar")
    public void saveUserAvatar(@PathVariable("username") String username, @RequestBody Map<String, String> avatarConfig) throws ItemNotUnlockedException {
        User user = userService.retrieveUser(username);
        List<Loot> loots = lootService.retrieveLoots(username);
        for (String avatarKey : avatarConfig.keySet()) {
            if (avatarConfig.get(avatarKey) != null && !avatarConfig.get(avatarKey).equals("empty")
                    && loots.stream().noneMatch(loot -> loot.getItemId().equals(avatarConfig.get(avatarKey)))) {
                throw new ItemNotUnlockedException();
            }
        }

        user.setAvatar(avatarConfig);
        userService.saveUser(user);
    }

    private class ItemNotUnlockedException extends Exception {}
}
