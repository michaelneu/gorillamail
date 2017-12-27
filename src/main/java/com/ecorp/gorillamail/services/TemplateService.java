package com.ecorp.gorillamail.services;

import com.ecorp.gorillamail.common.qualifiers.OptionTemplate;
import com.ecorp.gorillamail.entities.Template;
import com.ecorp.gorillamail.entities.Organization;
import com.ecorp.gorillamail.repositories.TemplateRepository;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import org.apache.logging.log4j.Logger;

@RequestScoped
public class TemplateService {
    @Inject
    private TemplateRepository templates;

    @Inject
    @OptionTemplate
    private Logger logger;

    public Template createTemplate(Organization organization) {
        Template template = new Template("New template", "<html>\n\t<head>\n\t<style>\n\t\t* {\n\t\tfont-family: sans-serif;\n\t\t}\n\t</style>\n\t</head>\n\t<body>\n\t<h1>Template</h1>\n\t<p>Start typing your template here...</p>\n\t</body>\n</html>", organization);

        template = templates.persist(template);
        template.setOrganization(organization);

        return saveTemplate(template);
    }

    public Template saveTemplate(Template template) {
        return templates.update(template);
    }
}
