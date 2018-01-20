package com.ecorp.gorillamail.services.mail;

import java.time.temporal.ChronoUnit;
import java.time.Instant;
import java.util.Properties;
import javax.enterprise.context.ApplicationScoped;
import javax.mail.Session;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;

@ApplicationScoped
public class SMTP {
    private static final boolean SMTP_SSL = true;

    private static final int
        SESSION_RECREATE_TIMEOUT_MINUTES = 10,
        SMTP_PORT = 465;

    private static final String
        SMTP_HOST = "",
        SMTP_USERNAME = "",
        SMTP_PASSWORD = "";

    private Instant lastTimeSessionCreated = Instant.now();
    private Session session;

    public void send(Email email) throws Exception {
        final long minutes = ChronoUnit.MINUTES.between(lastTimeSessionCreated, Instant.now());

        if (session != null && minutes < SESSION_RECREATE_TIMEOUT_MINUTES) {
            email.setMailSession(session);
        } else {
            email.setSSLOnConnect(SMTP_SSL);
            email.setHostName(SMTP_HOST);
            email.setSmtpPort(SMTP_PORT);
            email.setAuthenticator(new DefaultAuthenticator(SMTP_USERNAME, SMTP_PASSWORD));

            session = email.getMailSession();
            lastTimeSessionCreated = Instant.now();
        }

        email.send();
    }
}
