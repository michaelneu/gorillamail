package com.ecorp.gorillamail.beans;

import com.ecorp.gorillamail.common.ViewIds;
import com.ecorp.gorillamail.entities.BillingInformation;
import com.ecorp.gorillamail.entities.Organization;
import com.ecorp.gorillamail.entities.User;
import com.ecorp.gorillamail.services.CustomerService;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;

@Named( value = "organizationBean" )
@SessionScoped
public class OrganizationBean implements Serializable {
    @Inject
    private CustomerService customerService;

    @Getter
    @Setter
    private Organization currentOrganization = null;

    public String createOrganization(User user) {
        final Organization organization = new Organization("New organization", new BillingInformation());

        currentOrganization = customerService.saveOrganization(organization);
        currentOrganization.getUsers().add(user);
        saveOrganization();

        return ViewIds.EDIT_ORGANIZATION;
    }

    public String saveOrganization() {
        currentOrganization = customerService.saveOrganization(currentOrganization);

        return ViewIds.DASHBOARD;
    }

    public String editOrganization(Organization organization) {
        currentOrganization = organization;

        return ViewIds.EDIT_ORGANIZATION;
    }

    public void onModifyMembers(ActionEvent event) {
        saveOrganization();
    }

    public String addUser(User user) {
        currentOrganization.getUsers().add(user);
        saveOrganization();

        return ViewIds.EDIT_ORGANIZATION;
    }

    public String removeUser(User user) {
        currentOrganization.getUsers().remove(user);
        saveOrganization();

        return ViewIds.EDIT_ORGANIZATION;
    }
}
