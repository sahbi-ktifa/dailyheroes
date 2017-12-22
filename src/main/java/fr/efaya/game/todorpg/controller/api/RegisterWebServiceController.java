package fr.efaya.game.todorpg.controller.api;

import fr.efaya.game.todorpg.domain.Dashboard;
import fr.efaya.game.todorpg.domain.User;
import fr.efaya.game.todorpg.domain.pojo.RegisterCommand;
import fr.efaya.game.todorpg.service.DashboardService;
import fr.efaya.game.todorpg.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Sahbi Ktifa
 * created on 21/12/2017
 */
@RestController
@RequestMapping("/register")
public class RegisterWebServiceController {

    private UserService userService;
    private DashboardService dashboardService;

    public RegisterWebServiceController(UserService userService, DashboardService dashboardService) {
        this.userService = userService;
        this.dashboardService = dashboardService;
    }

    @PostMapping
    public void registrationProcess(@RequestBody RegisterCommand command) {
        Dashboard dashboard = new Dashboard();
        for (String username : command.getPlayerNames()) {
            User user = userService.saveUser(new User(username, command.getPassword()));
            dashboard.getUsers().add(user.getId());
        }
        dashboardService.saveDashboard(dashboard);
    }
}
