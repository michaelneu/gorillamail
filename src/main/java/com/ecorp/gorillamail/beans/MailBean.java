package com.ecorp.gorillamail.beans;

import com.ecorp.gorillamail.common.qualifiers.OptionMail;
import com.ecorp.gorillamail.common.ViewIds;
import com.ecorp.gorillamail.entities.Header;
import com.ecorp.gorillamail.entities.Mail;
import com.ecorp.gorillamail.entities.Template;
import com.ecorp.gorillamail.entities.User;
import com.ecorp.gorillamail.entities.Variable;
import com.ecorp.gorillamail.services.MailService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.Logger;

@Named( value = "mailBean" )
@SessionScoped
public class MailBean implements Serializable {
    @Inject
    private MailService mailService;

    @Inject
    @OptionMail
    private Logger logger;

    @Getter
    private Template currentTemplate;

    @Getter
    private List<Variable> variables = new ArrayList<>();

    @Getter
    private String error = "";

    public String prepareTestMail(Template template) {
        error = "";
        currentTemplate = template;
        variables.clear();

        return ViewIds.PREPARE_TEST_MAIL;
    }

    public String addVariable() {
        variables.add(new Variable());

        return ViewIds.PREPARE_TEST_MAIL;
    }

    public String sendMail(User user) {
        final Mail mail = new Mail();

        mail.setTemplate(currentTemplate);
        mail.setAd(true);

        mail.getHeaders().add(new Header("to", user.getEmail()));
        mail.getHeaders().add(new Header("subject", "gorillamail Test Mail"));

        mail.getVariables().addAll(variables);

        try {
            mailService.sendMail(user, mail);

            return ViewIds.EDIT_TEMPLATE;
        } catch (Exception exception) {
            error = exception.getMessage();
        }

        return ViewIds.PREPARE_TEST_MAIL;
    }
}
