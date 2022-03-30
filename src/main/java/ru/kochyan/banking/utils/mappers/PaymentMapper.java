package ru.kochyan.banking.utils.mappers;

import ru.kochyan.banking.dtos.PaymentDto;
import ru.kochyan.banking.entities.CheckingAccount;
import ru.kochyan.banking.entities.Payment;
import ru.kochyan.banking.entities.PaymentPurpose;

import java.util.Set;

public class PaymentMapper {
    public static PaymentDto toDto(Payment entity) {
        return PaymentDto.builder()
                .id(entity.getId())
                .value(entity.getValue().toString())
                .date(String.valueOf(entity.getDate()))
                .payer(IndividualEntityMapper.toDto(entity.getPayer()))
                .purpose(PaymentPurposeMapper.toDto(entity.getPurpose()))
                .checkingAccount(getCheckingAccValueByPurpose(entity.getLegalEntity().getCheckingAccounts(), entity.getPurpose()))
                .bankBranch(BankBranchMapper.toDto(entity.getBankBranch()))
                .build();
    }

    private static String getCheckingAccValueByPurpose(Set<CheckingAccount> accounts, PaymentPurpose purpose) {
        for (CheckingAccount acc : accounts) {
            if (acc.getPaymentPurposes().contains(purpose)) {
                return acc.getValue();
            }
        }
        return null;
    }
}
