package fr.efaya.game.dailyheroes.service;

import fr.efaya.game.dailyheroes.domain.User;

/**
 * @author Sahbi Ktifa
 * created on 19/12/2017
 */
public interface UserService {
    User retrieveUser(String username);
    User saveUser(User user);
}
