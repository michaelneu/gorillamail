package com.ecorp.gorillamail.beans;

import com.ecorp.gorillamail.common.ViewIds;
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

@Named( value = "userBean" )
@SessionScoped
public class UserBean implements Serializable {
    @Inject
    private CustomerService customerService;

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
    private boolean loggedIn;

    private long loggedInId;
    public User getLoggedInUser() {
        if (loggedIn) {
            return customerService.loadUserInformationById(loggedInId);
        }

        return new User();
    }

    public String signup() {
        try {
            customerService.signup(new User(name, email, password));
            setErrorMessage("");

            return ViewIds.LOGIN;
        } catch (SignupException ex) {
            this.setErrorMessage(ex.getMessage());
        }

        return ViewIds.SIGNUP;
    }

    public String login() {
        try {
            final User user = customerService.login(email, password);
            loggedInId = user.getId();
            loggedIn = true;

            setErrorMessage("");

            return ViewIds.DASHBOARD;
        } catch (LoginException ex) {
            this.setErrorMessage(ex.getMessage());
        }

        return ViewIds.LOGIN;
    }

    public String logout() {
        loggedIn = false;

        return ViewIds.LOGIN;
    }
}
