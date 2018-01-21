package com.ecorp.gorillamail.services;

import com.ecorp.gorillamail.entities.Organization;
import com.ecorp.gorillamail.entities.Template;

public interface TemplateServiceIF {
    Template createTemplate(Organization organization);
    Template saveTemplate(Template template);
    String getRedirectUrl(String target);
    Template fetchTemplate(Template template);
}
