package fr.efaya.game.dailyheroes.service;

import fr.efaya.game.dailyheroes.domain.Dashboard;
import fr.efaya.game.dailyheroes.repository.DashboardRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @author Sahbi Ktifa
 * created on 19/12/2017
 */
@Service
public class DashboardServiceImpl implements DashboardService {
    private DashboardRepository repository;

    public DashboardServiceImpl(DashboardRepository repository) {
        this.repository = repository;
    }

    @Override
    public Dashboard retrieveDashboard(String dashboardId) {
        return repository.findOne(dashboardId);
    }

    @Override
    public Dashboard saveDashboard(Dashboard dashboard) {
        if (dashboard.getId() == null) {
            dashboard.setId(UUID.randomUUID().toString());
        }
        return repository.save(dashboard);
    }

    @Override
    public List<Dashboard> retrieveDashboardsForUser(String username) {
        return repository.findAllByUsersContains(username);
    }
}
