package fr.efaya.game.todorpg.repository;

import fr.efaya.game.todorpg.domain.User;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Sahbi Ktifa
 * created on 19/12/2017
 */
public interface UserRepository extends CrudRepository<User, String> {

    User findByUsername(String username);
}
