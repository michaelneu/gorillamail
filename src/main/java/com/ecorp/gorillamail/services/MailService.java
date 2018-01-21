package com.ecorp.gorillamail.services;

import com.ecorp.gorillamail.common.qualifiers.OptionMail;
import com.ecorp.gorillamail.entities.BillingInformation;
import com.ecorp.gorillamail.entities.Mail;
import com.ecorp.gorillamail.entities.Organization;
import com.ecorp.gorillamail.entities.Template;
import com.ecorp.gorillamail.entities.User;
import com.ecorp.gorillamail.repositories.MailRepository;
import com.ecorp.gorillamail.repositories.TemplateRepository;
import com.ecorp.gorillamail.services.exceptions.LoginException;
import com.ecorp.gorillamail.services.exceptions.MailException;
import com.ecorp.gorillamail.services.exceptions.MailSendException;
import com.ecorp.gorillamail.services.external.DebitServiceIF;
import com.ecorp.gorillamail.services.external.exceptions.DebitException;
import com.ecorp.gorillamail.services.mail.CompiledMail;
import com.ecorp.gorillamail.services.mail.MailCompiler;
import com.ecorp.gorillamail.services.mail.SMTP;
import java.math.BigDecimal;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.jws.WebService;
import javax.jws.WebMethod;
import org.apache.commons.mail.HtmlEmail;
import org.apache.logging.log4j.Logger;

@RequestScoped
@WebService
public class MailService implements MailServiceIF {
    private static final BigDecimal MAIL_PRICE = new BigDecimal("0.01");

    @Inject
    private SMTP smtp;

    @Inject
    private MailCompiler compiler;

    @Inject
    private MailRepository mails;

    @Inject
    private TemplateRepository templates;

    @Inject
    private CustomerService customerService;
    
    @Inject
    private DebitServiceIF debitService;
    
    @Inject
    @OptionMail
    private Logger logger;

    private boolean isAllowedToSendMail(User user, Mail mail) throws MailException {
        try {
            user = customerService.login(user.getEmail(), user.getPassword());
        } catch (LoginException exception) {
            throw new MailSendException("invalid credentials");
        }

        for (Organization organization : user.getOrganizations()) {
            for (Template template : organization.getTemplates()) {
                if (template.equals(mail.getTemplate())) {
                    return true;
                }
            }
        }

        return false;
    }
    
    private void payMail(Mail mail) throws DebitException {
        final long templateId = mail.getTemplate().getId();
        final Template resolvedTemplate = templates.findById(templateId);
        final Organization organization = resolvedTemplate.getOrganization();
        final BillingInformation billingInformation = organization.getBillingInformation();
        
        debitService.requestDebit(MAIL_PRICE, billingInformation, "gorillamail '" + organization.getName() + "'");
    }

    @WebMethod
    @Override
    public Mail sendMail(User user, Mail mail) throws MailException {
        if (user == null) {
            throw new IllegalArgumentException("expected user to not be null");
        }

        if (mail == null) {
            throw new IllegalArgumentException("expected mail to not be null");
        }

        if (!isAllowedToSendMail(user, mail)) {
            throw new MailSendException("user not allowed to send a mail with this template");
        }
        
        if (!mail.isAd()) {
            try {
                payMail(mail);
            } catch (DebitException exception) {
                logger.warn("failed to pay for mail - inserting ad");
                mail.setAd(true);
            }
        }

        final CompiledMail compiledMail = compiler.compileMail(mail);

        try {
            final HtmlEmail email = new HtmlEmail();

            email.setFrom("noreply@gorillamail.space", compiledMail.getFrom());
            email.setSubject(compiledMail.getSubject());
            email.setHtmlMsg(compiledMail.getBody());
            email.addTo(compiledMail.getTo());

            smtp.send(email);
        } catch (Exception exception) {
            logger.fatal("could not send mail - check mail configuration!");
            
            throw new MailSendException("error sending mail", exception);
        }

        return mails.persist(mail);
    }
}
