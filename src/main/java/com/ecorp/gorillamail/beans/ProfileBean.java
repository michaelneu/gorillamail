package com.ecorp.gorillamail.beans;

import com.ecorp.gorillamail.common.ViewIds;
import com.ecorp.gorillamail.entities.User;
import com.ecorp.gorillamail.services.CustomerService;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;

@Named( value = "profileBean" )
@SessionScoped
public class ProfileBean implements Serializable {
    @Inject
    private CustomerService customerService;

    @Getter
    private String error = "";

    @Getter
    private User currentUser;

    @Getter
    @Setter
    private String password1;

    @Getter
    @Setter
    private String password2;

    public String editProfile(User user) {
        currentUser = user;

        return ViewIds.PROFILE;
    }

    public String saveProfile() {
        boolean updatePassword = false;

        if (password1.length() > 0) {
            if (!password1.equals(password2)) {
                error = "passwords didn't match";
                return ViewIds.PROFILE;
            }

            updatePassword = true;
        }

        error = "";

        customerService.saveUser(currentUser);

        if (updatePassword) {
            customerService.updatePassword(currentUser, password1);
        }

        return ViewIds.DASHBOARD;
    }
}
