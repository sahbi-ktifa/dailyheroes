package fr.efaya.game.dailyheroes.controller.api;

import fr.efaya.game.dailyheroes.domain.Dashboard;
import fr.efaya.game.dailyheroes.domain.User;
import fr.efaya.game.dailyheroes.domain.pojo.RegisterCommand;
import fr.efaya.game.dailyheroes.event.CreatedUserEvent;
import fr.efaya.game.dailyheroes.event.UserAddedInDashboard;
import fr.efaya.game.dailyheroes.service.DashboardService;
import fr.efaya.game.dailyheroes.service.UserService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.management.InstanceAlreadyExistsException;

/**
 * @author Sahbi Ktifa
 * created on 21/12/2017
 */
@RestController
@RequestMapping("/register")
public class RegisterWebServiceController {

    private UserService userService;
    private DashboardService dashboardService;
    private ApplicationEventPublisher publisher;

    public RegisterWebServiceController(UserService userService, DashboardService dashboardService, ApplicationEventPublisher publisher) {
        this.userService = userService;
        this.dashboardService = dashboardService;
        this.publisher = publisher;
    }

    @GetMapping("{username}/exist")
    public void checkUser(@PathVariable("username") String username) throws InstanceAlreadyExistsException {
        User user = userService.retrieveUser(username);
        if (user != null) {
            throw new InstanceAlreadyExistsException(username);
        }
    }

    @PostMapping
    public void registrationProcess(@RequestBody RegisterCommand command) {
        Dashboard dashboard = new Dashboard();
        dashboard.setName(command.getDashboardName());
        for (String username : command.getPlayerNames()) {
            User existingUser = userService.retrieveUser(username);
            if (existingUser != null) {
                dashboard.getPendingUsers().add(username);
                publisher.publishEvent(new UserAddedInDashboard(this, null, existingUser, dashboard));
            } else {
                User user = userService.saveUser(new User(username, command.getPassword()));
                dashboard.getUsers().add(user.getUsername());
                publisher.publishEvent(new CreatedUserEvent(this, null, user));
            }
        }
        dashboardService.saveDashboard(dashboard);
    }
}
