package ru.kochyan.banking.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kochyan.banking.entities.AbstractEntity;
import ru.kochyan.banking.repos.AbstractRepo;
import ru.kochyan.banking.services.AbstractService;

import java.util.List;
import java.util.Optional;

@Service
public abstract class AbstractServiceImpl<E extends AbstractEntity> implements AbstractService<E> {
    protected final AbstractRepo<E> abstractRepo;

    @Autowired
    public AbstractServiceImpl(AbstractRepo<E> abstractRepo) {
        this.abstractRepo = abstractRepo;
    }

    @Override
    public List<E> findAll() {
        return abstractRepo.findAll();
    }

    @Override
    public Optional<E> find(Long id) {
        return abstractRepo.findById(id);
    }

    @Override
    public E save(E e) {
        return abstractRepo.save(e);
    }

    @Override
    public void delete(E e) {
        abstractRepo.delete(e);
    }
}
