package ru.kochyan.banking.utils.mappers;

import ru.kochyan.banking.dtos.BankBranchDto;
import ru.kochyan.banking.entities.BankBranch;

public class BankBranchMapper {
    public static BankBranchDto toDto(BankBranch entity) {
        return BankBranchDto.builder()
                .id(entity.getId())
                .address(entity.getAddress())
                .build();
    }
}
