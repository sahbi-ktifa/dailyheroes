package fr.efaya.game.dailyheroes.service;

import fr.efaya.game.dailyheroes.domain.User;
import fr.efaya.game.dailyheroes.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author Sahbi Ktifa
 * created on 19/12/2017
 */
@Service
public class UserServiceImpl implements UserService {
    private UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User retrieveUser(String username) {
        User user = repository.findOne(username);
        if (user != null) {
            user.setPassword(null);
        }
        return user;
    }

    @Override
    public User saveUser(User user) {
        if (user.getCreationDate() == null) {
            user.setCreationDate(new Date());
        }
        if (user.getPassword() == null) {
            User refUser = repository.findOne(user.getUsername());
            user.setPassword(refUser.getPassword());
        }
        return repository.save(user);
    }
}
