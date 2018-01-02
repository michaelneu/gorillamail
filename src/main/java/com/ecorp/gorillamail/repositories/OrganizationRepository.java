package com.ecorp.gorillamail.repositories;

import com.ecorp.gorillamail.entities.Organization;
import javax.enterprise.context.RequestScoped;
import javax.transaction.Transactional;

@RequestScoped
@Transactional
public class OrganizationRepository extends Repository<Organization> { }
