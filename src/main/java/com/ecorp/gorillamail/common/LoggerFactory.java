package com.ecorp.gorillamail.common;

import com.ecorp.gorillamail.services.CustomerService;
import com.ecorp.gorillamail.services.MailService;
import com.ecorp.gorillamail.services.TemplateService;
import com.ecorp.gorillamail.common.qualifiers.OptionCustomer;
import com.ecorp.gorillamail.common.qualifiers.OptionMail;
import com.ecorp.gorillamail.common.qualifiers.OptionTemplate;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@ApplicationScoped
public class LoggerFactory {
    @Produces
    @ApplicationScoped
    @OptionCustomer
    public Logger getCustomerLogger() {
        return LogManager.getLogger(CustomerService.class);
    }

    @Produces
    @ApplicationScoped
    @OptionTemplate
    public Logger getTemplateLogger() {
        return LogManager.getLogger(TemplateService.class);
    }

    @Produces
    @ApplicationScoped
    @OptionMail
    public Logger getMailLogger() {
        return LogManager.getLogger(MailService.class);
    }
}
