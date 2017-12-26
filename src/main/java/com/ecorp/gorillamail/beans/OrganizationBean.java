package com.ecorp.gorillamail.beans;

import com.ecorp.gorillamail.common.qualifiers.OptionCustomer;
import com.ecorp.gorillamail.entities.BillingInformation;
import com.ecorp.gorillamail.entities.Organization;
import com.ecorp.gorillamail.entities.User;
import com.ecorp.gorillamail.services.CustomerService;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.Logger;

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
        currentOrganization = customerService.saveOrganization(currentOrganization);

        return "edit_organization";
    }

    public String saveOrganization() {
        customerService.saveOrganization(currentOrganization);

        return "dashboard";
    }

    public String editOrganization(Organization organization) {
        currentOrganization = organization;

        return "edit_organization";
    }
}
