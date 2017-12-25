package com.ecorp.gorillamail.repositories;

import com.ecorp.gorillamail.entities.AbstractLongEntity;
import java.util.List;
import java.util.Map;

public interface RepositoryIF<T extends AbstractLongEntity> {
    T findById(long id);
    List<T> findAll();

    List<T> query(String namedQueryName, Map<String, Object> parameters);

    T persist(T entity);
    T update(T entity);
    void remove(long id);
}
