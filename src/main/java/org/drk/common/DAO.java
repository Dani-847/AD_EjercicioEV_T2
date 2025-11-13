package org.drk.common;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz genérica para operaciones CRUD (Create, Read, Update, Delete) en una entidad de tipo T.
 */

public interface DAO<T> {
    /* Métodos de escritura */
    Optional<T> save(T t);
    Optional<T> update(T t);
    Optional<T> delete(T t);

    /* Métodos de lectura */
    List<T> findAll();
    Optional<T> findById(Integer id);
}
