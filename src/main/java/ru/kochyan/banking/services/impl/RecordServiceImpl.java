package ru.kochyan.banking.services.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.kochyan.banking.entities.Record;
import ru.kochyan.banking.repos.RecordRepo;
import ru.kochyan.banking.services.RecordService;

@Service
public class RecordServiceImpl extends AbstractServiceImpl<Record> implements RecordService {
    private final RecordRepo recordRepo;

    public RecordServiceImpl(RecordRepo recordRepo) {
        super(recordRepo);
        this.recordRepo = recordRepo;
    }

    @Override
    public Page<Record> findAll(Pageable pageable) {
        return recordRepo.findAll(pageable);
    }
}
