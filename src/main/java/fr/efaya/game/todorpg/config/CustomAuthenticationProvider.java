package fr.efaya.game.todorpg.config;

import fr.efaya.game.todorpg.domain.User;
import fr.efaya.game.todorpg.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * @author Sahbi Ktifa
 * created on 21/12/2017
 */
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        final String name = authentication.getName();
        final String password = authentication.getCredentials().toString();

        User user = userService.retrieveUserUsingName(name);
        if (user == null || !password.equals(user.getPassword())) {
            throw new BadCredentialsException("Username is unknown or password is incorrect");
        }
        return new UsernamePasswordAuthenticationToken(name, password, Collections.singletonList(new SimpleGrantedAuthority("SIMPLE_USER")));
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
