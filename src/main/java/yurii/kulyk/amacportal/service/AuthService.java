package yurii.kulyk.amacportal.service;


import yurii.kulyk.amacportal.entity.User;
import yurii.kulyk.amacportal.repository.UserRepository;
import yurii.kulyk.amacportal.util.JWTIssuer;
import yurii.kulyk.amacportal.util.PasswordUtil;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class AuthService {

    @Inject
    UserRepository userRepository;

    public String login(String username, String password) {

        User user = userRepository.findByEmail(username);
        if (user == null) {
            throw new SecurityException("Invalid credentials");
        }


        if (!PasswordUtil.verify(password, user.getPasswordHash())) {
            throw new SecurityException("Invalid credentials");
        }

        return JWTIssuer.issue(user);
    }
}

