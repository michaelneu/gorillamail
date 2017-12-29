package com.ecorp.gorillamail.repositories;

import com.ecorp.gorillamail.entities.ExternalResource;
import com.ecorp.gorillamail.entities.Template;
import javax.enterprise.context.RequestScoped;
import javax.transaction.Transactional;

@RequestScoped
@Transactional
public class TemplateRepository extends Repository<Template> {
    private void fetchLinks(Template template) {
        for (ExternalResource link : template.getLinks()) {
            // only fetch, don't do anything
        }
    }

    @Override
    public Template findById(long id) {
        final Template template = super.findById(id);

        fetchLinks(template);

        return template;
    }
}
