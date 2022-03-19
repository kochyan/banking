package ru.kochyan.banking.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import ru.kochyan.banking.entities.AbstractEntity;

@NoRepositoryBean
public interface AbstractRepo<E extends AbstractEntity> extends JpaRepository<E, Long> {
}
