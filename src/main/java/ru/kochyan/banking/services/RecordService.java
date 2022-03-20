package ru.kochyan.banking.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.kochyan.banking.entities.Record;

public interface RecordService extends AbstractService<Record>{
    Page<Record> findAll(Pageable pageable);
}
