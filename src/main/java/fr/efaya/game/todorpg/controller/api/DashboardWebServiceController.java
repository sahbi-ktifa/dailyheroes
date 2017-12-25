package fr.efaya.game.todorpg.controller.api;

import fr.efaya.game.todorpg.domain.Dashboard;
import fr.efaya.game.todorpg.service.DashboardService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Sahbi Ktifa
 * created on 24/12/2017
 */
@RestController
@RequestMapping("dashboard")
public class DashboardWebServiceController {

    private DashboardService dashboardService;

    public DashboardWebServiceController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("{username}")
    public Dashboard retrieveDashboardForUser(@PathVariable("username") String username) {
        return dashboardService.retrieveDashboardForUser(username);
    }
}
