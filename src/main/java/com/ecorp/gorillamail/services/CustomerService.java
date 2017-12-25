package com.ecorp.gorillamail.services;

import com.ecorp.gorillamail.common.qualifiers.OptionCustomer;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.apache.logging.log4j.Logger;

@RequestScoped
public class CustomerService {
    @PersistenceContext( unitName = "persistence" )
    private EntityManager entityManager;

    @Inject
    @OptionCustomer
    private Logger serviceLogger;
}
