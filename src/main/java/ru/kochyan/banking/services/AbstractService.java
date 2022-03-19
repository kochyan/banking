package ru.kochyan.banking.services;

import ru.kochyan.banking.entities.AbstractEntity;

import java.util.List;
import java.util.Optional;

public interface AbstractService<E extends AbstractEntity> {
    List<E> findAll();
    Optional<E> find(Long id);
    E save(E e);
    void delete(E e);
}
