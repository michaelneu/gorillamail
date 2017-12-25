package com.ecorp.gorillamail.services;

import com.ecorp.gorillamail.common.qualifiers.OptionCustomer;
import com.ecorp.gorillamail.entities.User;
import com.ecorp.gorillamail.repositories.UserRepository;
import com.ecorp.gorillamail.services.exceptions.LoginException;
import com.ecorp.gorillamail.services.exceptions.SignupException;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import org.apache.logging.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;

@RequestScoped
public class CustomerService {
    @Inject
    private UserRepository users;

    @Inject
    @OptionCustomer
    private Logger logger;

    public void signup(User user) throws IllegalArgumentException, SignupException {
        if (user == null) {
            throw new IllegalArgumentException("no user provided");
        }

        final List<User> found = users.findByEmail(user.getEmail());

        if (found.size() > 0) {
            throw new SignupException("user '" + user.getEmail() + "' already exists");
        }

        if (!user.getEmail().matches("[A-Za-z0-9\\.-_]+@[A-Za-z0-9\\.-_]+\\.[A-Za-z0-9\\.-_]+")) {
            throw new SignupException("invalid email");
        }

        if (user.getName().length() < 3) {
            throw new SignupException("provide at least a 3 character name");
        }

        if (user.getPassword().length() == 0) {
            throw new SignupException("please provide a password");
        }

        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        users.persist(user);
    }

    public User login(String email, String password) throws LoginException {
        final List<User> found = users.findByEmail(email);

        if (found.size() == 0) {
            throw new LoginException("No user found with this email address");
        }

        final User user = found.get(0);
        final boolean isPasswordCorrect = BCrypt.checkpw(password, user.getPassword());

        if (!isPasswordCorrect) {
            throw new LoginException("The given password didn't match our stored one");
        }

        return user;
    }
}
