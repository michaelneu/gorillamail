package com.ecorp.gorillamail.repositories;

import com.ecorp.gorillamail.entities.AbstractLongEntity;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

public abstract class Repository<T extends AbstractLongEntity> implements RepositoryIF<T> {
    @PersistenceContext(unitName = "persistence")
    private EntityManager entityManager;

    private final Class<AbstractLongEntity> clazz;

    /**
     * adapted from:
     * https://stackoverflow.com/questions/3888575/single-dao-generic-crud-methods-jpa-hibernate-spring
     */
    protected Repository() {
        final ParameterizedType genericSuperClass = (ParameterizedType)getClass().getSuperclass().getGenericSuperclass();
        
        clazz = (Class<AbstractLongEntity>)genericSuperClass.getActualTypeArguments()[0];
    }

    @Override
    public T findById(long id) {
        final T entity = (T)entityManager.find(clazz, id);

        if (entity == null) {
            throw new EntityNotFoundException("Keine Entity vom Typ " + this.clazz.getSimpleName() + " mit Id = " + id + " gefunden!");
        }

        return entity;
    }

    @Override
    public List<T> findAll() {
        final String statement = "SELECT t FROM " + clazz.getSimpleName() + " t";
        final Query query = entityManager.createQuery(statement);
        return query.getResultList();
    }

    /**
     * adapted from:
     * http://www.adam-bien.com/roller/abien/entry/generic_crud_service_aka_dao
     */
    @Override
    public List<T> query(String namedQueryName, Map<String, Object> parameters) {
        @SuppressWarnings("unsafe")
        final Set<Map.Entry<String, Object>> params = parameters.entrySet();
        final Query query = entityManager.createNamedQuery(namedQueryName);

        params.forEach((entry) -> {
            query.setParameter(entry.getKey(), entry.getValue());
        });

        return query.getResultList();
    }

    @Override
    public T persist(T entity) {
        entityManager.persist(entity);

        return entity;
    }

    @Override
    public T update(T entity) {
        final T updatedEntity = entityManager.merge(entity);

        return persist(updatedEntity);
    }

    @Override
    public void remove(long id) {
        final T entity = findById(id);

        entityManager.remove(entity);
    }
}
