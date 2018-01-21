package com.ecorp.gorillamail.services;

import com.ecorp.gorillamail.entities.Organization;
import com.ecorp.gorillamail.entities.User;
import com.ecorp.gorillamail.services.exceptions.LoginException;
import com.ecorp.gorillamail.services.exceptions.SignupException;

public interface CustomerServiceIF {
    void signup(User user) throws IllegalArgumentException, SignupException;
    User login(String email, String password) throws LoginException;
    User saveUser(User user);
    User updatePassword(User user, String password);
    User loadUserInformationById(long id);
    Organization saveOrganization(Organization organization);
}
