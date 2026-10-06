package com.rps.server.repository.base;

import java.util.List;
import java.util.Optional;

public interface BaseRepo<T, ID> {
    T save(T entity);
    Optional<T> findById(ID id);
    List<T> findAll();
    void delete(T entity);
}
