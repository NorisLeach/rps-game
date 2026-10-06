package com.rps.server.repository.base;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

public abstract class BaseRepoImpl<T, ID> implements BaseRepo<T, ID> {

    @PersistenceContext
    protected EntityManager entityManager;

    private final Class<T> entityClass;

    protected BaseRepoImpl(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    @Transactional
    public T save(T entity) {
        return entityManager.merge(entity);
    }

    @Override
    public Optional<T> findById(ID id) {
        return Optional.ofNullable(entityManager.find(entityClass, id));
    }

    @Override
    public List<T> findAll() {
        return entityManager.createQuery(
                "FROM " + entityClass.getSimpleName(), entityClass
        ).getResultList();
    }

    @Override
    @Transactional
    public void delete(T entity) {
        entityManager.remove(
                entityManager.contains(entity) ? entity : entityManager.merge(entity)
        );
    }
}
