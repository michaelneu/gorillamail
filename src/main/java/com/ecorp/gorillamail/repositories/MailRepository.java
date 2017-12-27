package com.ecorp.gorillamail.repositories;

import com.ecorp.gorillamail.entities.Mail;
import javax.enterprise.context.RequestScoped;
import javax.transaction.Transactional;

@RequestScoped
@Transactional
public class MailRepository extends Repository<Mail> { }
