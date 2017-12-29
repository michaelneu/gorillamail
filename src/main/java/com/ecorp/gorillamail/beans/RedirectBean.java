package com.ecorp.gorillamail.beans;

import com.ecorp.gorillamail.services.TemplateService;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;

@Named( value = "redirectBean" )
@SessionScoped
public class RedirectBean implements Serializable {
    @Inject
    private TemplateService templateService;

    @Getter
    @Setter
    private String target;

    public String getRedirectUrl() {
        return templateService.getRedirectUrl(target);
    }
}
