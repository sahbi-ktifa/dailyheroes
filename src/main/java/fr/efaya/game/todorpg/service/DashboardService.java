package fr.efaya.game.todorpg.service;

import fr.efaya.game.todorpg.domain.Dashboard;

/**
 * @author Sahbi Ktifa
 * created on 19/12/2017
 */
public interface DashboardService {
    Dashboard saveDashboard(Dashboard dashboard);

    Dashboard retrieveDashboardForUser(String userId);
}
