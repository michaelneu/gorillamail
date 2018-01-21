package com.ecorp.gorillamail.services;

import com.ecorp.gorillamail.entities.Mail;
import com.ecorp.gorillamail.entities.User;
import com.ecorp.gorillamail.services.exceptions.MailException;

public interface MailServiceIF {
    Mail sendMail(User user, Mail mail) throws MailException;
}
