package com.ecorp.gorillamail.repositories;

import com.ecorp.gorillamail.entities.ExternalResource;
import com.ecorp.gorillamail.entities.Template;
import com.ecorp.gorillamail.entities.VisitorInformation;
import javax.enterprise.context.RequestScoped;
import javax.transaction.Transactional;

@RequestScoped
@Transactional
public class TemplateRepository extends Repository<Template> {
    @Override
    public Template findById(long id) {
        final Template template = super.findById(id);

        for (ExternalResource link : template.getLinks()) {
            // only fetch, don't do anything
        }
        
        // fetch billing information
        template.getOrganization()
                .getBillingInformation();

        return template;
    }

    public Template resolveById(long id) {
        final Template template = findById(id);

        for (ExternalResource link : template.getLinks()) {
            for (VisitorInformation visitor : link.getVisitors()) {
                // only fetch, don't do anything
            }
        }

        return template;
    }
}
