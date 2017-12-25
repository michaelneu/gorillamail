package com.ecorp.gorillamail.beans;

import com.ecorp.gorillamail.common.qualifiers.OptionCustomer;
import com.ecorp.gorillamail.entities.User;
import com.ecorp.gorillamail.services.CustomerService;
import com.ecorp.gorillamail.services.exceptions.LoginException;
import com.ecorp.gorillamail.services.exceptions.SignupException;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.Logger;

@Named( value = "userBean" )
@SessionScoped
public class UserBean implements Serializable {
    @Inject
    private CustomerService customerService;

    @Inject
    @OptionCustomer
    private Logger logger;

    @Getter
    @Setter
    private String errorMessage = "";

    @Getter
    @Setter
    private String email;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String password;

    @Getter
    @Setter
    private User loggedInUser = null;

    public String signup() {
        logger.info("Signing up " + email + " (" + name + ")");

        try {
            customerService.signup(new User(name, email, password));
            this.setErrorMessage("");

            return "login";
        } catch (SignupException ex) {
            this.setErrorMessage(ex.getMessage());
        }

        return "signup";
    }

    public String login() {
        logger.info("Logging in " + email);

        try {
            loggedInUser = customerService.login(email, password);
            this.setErrorMessage("");

            return "dashboard";
        } catch (LoginException ex) {
            this.setErrorMessage(ex.getMessage());
        }

        return "login";
    }

    public String logout() {
        loggedInUser = null;

        return "login";
    }

    public boolean getIsLoggedIn() {
        return loggedInUser != null;
    }
}
