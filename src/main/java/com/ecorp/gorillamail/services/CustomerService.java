package com.ecorp.gorillamail.services;

import com.ecorp.gorillamail.common.qualifiers.OptionCustomer;
import com.ecorp.gorillamail.entities.Organization;
import com.ecorp.gorillamail.entities.User;
import com.ecorp.gorillamail.repositories.OrganizationRepository;
import com.ecorp.gorillamail.repositories.UserRepository;
import com.ecorp.gorillamail.services.exceptions.LoginException;
import com.ecorp.gorillamail.services.exceptions.SignupException;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import org.apache.logging.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;

@RequestScoped
public class CustomerService implements CustomerServiceIF {
    @Inject
    private UserRepository users;

    @Inject
    private OrganizationRepository organizations;

    @Inject
    @OptionCustomer
    private Logger logger;

    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    @Override
    public void signup(User user) throws IllegalArgumentException, SignupException {
        logger.info("signing up " + user.getEmail());

        if (user == null) {
            throw new IllegalArgumentException("no user provided");
        }

        final List<User> found = users.findByEmail(user.getEmail());

        if (found.size() > 0) {
            throw new SignupException("user '" + user.getEmail() + "' already exists");
        }

        if (!user.getEmail().matches("[A-Za-z0-9\\._-]+@[A-Za-z0-9\\._-]+\\.[A-Za-z0-9\\._-]+")) {
            throw new SignupException("invalid email");
        }

        if (user.getName().length() < 3) {
            throw new SignupException("provide at least a 3 character name");
        }

        if (user.getPassword().length() == 0) {
            throw new SignupException("please provide a password");
        }

        user.setPassword(hashPassword(user.getPassword()));
        users.persist(user);
    }

    private boolean secureCompare(String a, String b) {
        if (a.length() != b.length()) {
            return false;
        }

        boolean areEqual = true;

        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i) != b.charAt(i)) {
                areEqual = false;
            }
        }

        return areEqual;
    }

    private boolean isPasswordCorrect(String expectedPassword, String actualPassword) {
        final boolean matchesHash = BCrypt.checkpw(actualPassword, expectedPassword),
                      matchesAsPlaintext = secureCompare(expectedPassword, actualPassword);
        
        return matchesHash || matchesAsPlaintext;
    }

    @Override
    public User login(String email, String password) throws LoginException {
        logger.info("logging in " + email);
        final List<User> found = users.findByEmail(email);

        if (found.isEmpty()) {
            throw new LoginException("No user found with this email address");
        }

        final User user = found.get(0);

        if (!isPasswordCorrect(user.getPassword(), password)) {
            throw new LoginException("The given password didn't match our stored one");
        }

        return user;
    }

    @Override
    public User saveUser(User user) {
        return users.update(user);
    }

    @Override
    public User updatePassword(User user, String password) {
        logger.info("update password of " + user.getEmail());
        final String hashedPassword = hashPassword(password);

        user.setPassword(hashedPassword);

        return users.update(user);
    }

    @Override
    public User loadUserInformationById(long id) {
        return users.findById(id);
    }

    @Override
    public Organization saveOrganization(Organization organization) {
        if (organization.getId() == 0) {
            return organizations.persist(organization);
        }

        return organizations.update(organization);
    }
}
