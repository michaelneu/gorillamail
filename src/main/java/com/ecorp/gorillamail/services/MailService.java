package com.ecorp.gorillamail.services;

import com.ecorp.gorillamail.common.qualifiers.OptionMail;
import com.ecorp.gorillamail.entities.Mail;
import com.ecorp.gorillamail.entities.User;
import com.ecorp.gorillamail.repositories.MailRepository;
import com.ecorp.gorillamail.repositories.TemplateRepository;
import com.ecorp.gorillamail.services.exceptions.MailException;
import com.ecorp.gorillamail.services.exceptions.MailSendException;
import com.ecorp.gorillamail.services.mail.CompiledMail;
import com.ecorp.gorillamail.services.mail.MailCompiler;
import com.ecorp.gorillamail.services.mail.MailServiceIF;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.HtmlEmail;
import org.apache.logging.log4j.Logger;

@RequestScoped
public class MailService implements MailServiceIF {
    private static final int SMTP_PORT = 25;
    private static final String
        SMTP_HOST = "maildev",
        SMTP_USERNAME = "username",
        SMTP_PASSWORD = "password";

    @Inject
    private MailCompiler compiler;

    @Inject
    private MailRepository mails;

    @Inject
    @OptionMail
    private Logger logger;

    @Override
    public Mail sendMail(User user, Mail mail) throws MailException {
        if (user == null) {
            throw new IllegalArgumentException("expected user to not be null");
        }

        if (mail == null) {
            throw new IllegalArgumentException("expected mail to not be null");
        }

        final CompiledMail compiledMail = compiler.compileMail(mail);

        try {
            final HtmlEmail email = new HtmlEmail();

            email.setHostName(SMTP_HOST);
            email.setSmtpPort(SMTP_PORT);
            email.setAuthenticator(new DefaultAuthenticator(SMTP_USERNAME, SMTP_PASSWORD));

            email.setFrom(compiledMail.getFrom());
            email.setSubject(compiledMail.getSubject());
            email.setHtmlMsg(compiledMail.getBody());
            email.addTo(compiledMail.getTo());

            email.send();
        } catch (Exception exception) {
            throw new MailSendException("error sending mail", exception);
        }

        return mails.persist(mail);
    }
}
