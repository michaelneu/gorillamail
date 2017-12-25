package com.ecorp.gorillamail.repositories;

import com.ecorp.gorillamail.entities.Organization;
import com.ecorp.gorillamail.entities.User;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.RequestScoped;
import javax.transaction.Transactional;

@RequestScoped
@Transactional
public class OrganizationRepository extends Repository<Organization> { }
