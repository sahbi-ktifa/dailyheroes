package fr.efaya.game.todorpg.controller.api;

import fr.efaya.game.todorpg.Constants;
import fr.efaya.game.todorpg.domain.User;
import fr.efaya.game.todorpg.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Sahbi Ktifa
 * created on 24/12/2017
 */
@RestController
@RequestMapping("user")
public class UserWebServiceController {

    private UserService userService;

    public UserWebServiceController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("{username}")
    public User retrieveUser(@PathVariable("username") String username) {
        return userService.retrieveUser(username);
    }

    @GetMapping("{username}/{userLevel}")
    public Integer retrieveUserNextLevelCap(@PathVariable("username") String username, @PathVariable("userLevel") Integer userLevel) {
        return Constants.levelsToExp.get(userLevel + 1);
    }
}
