package com.ecorp.gorillamail.repositories;

import com.ecorp.gorillamail.entities.ExternalResource;
import com.ecorp.gorillamail.entities.Template;
import com.ecorp.gorillamail.entities.VisitorInformation;
import javax.enterprise.context.RequestScoped;
import javax.transaction.Transactional;

@RequestScoped
@Transactional
public class TemplateRepository extends Repository<Template> { }
