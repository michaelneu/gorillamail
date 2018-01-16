package com.ecorp.gorillamail.beans;

import com.ecorp.gorillamail.common.ViewIds;
import com.ecorp.gorillamail.entities.Organization;
import com.ecorp.gorillamail.entities.Template;
import com.ecorp.gorillamail.services.CustomerService;
import com.ecorp.gorillamail.services.TemplateService;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;

@Named( value = "templateBean" )
@SessionScoped
public class TemplateBean implements Serializable {
    @Inject
    private TemplateService templateService;

    @Inject
    private CustomerService customerService;

    @Getter
    private Template currentTemplate;

    public String createTemplate(Organization organization) {
        currentTemplate = templateService.createTemplate(organization);

        return ViewIds.EDIT_TEMPLATE;
    }

    public String editTemplate(Template template) {
        currentTemplate = template;

        return ViewIds.EDIT_TEMPLATE;
    }

    public String saveTemplate() {
        templateService.saveTemplate(currentTemplate);

        return ViewIds.DASHBOARD;
    }

    public void onPrepareTestMail(ActionEvent event) {
        saveTemplate();
    }
}
