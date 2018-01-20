package com.ecorp.gorillamail.repositories;

import com.ecorp.gorillamail.entities.Template;
import javax.enterprise.context.RequestScoped;
import javax.transaction.Transactional;

@RequestScoped
@Transactional
public class TemplateRepository extends Repository<Template> { }
