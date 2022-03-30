package ru.kochyan.banking.dtos;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@ToString
public class PaymentDto {
    private Long id;
    private String date;
    private String value;

    private IndividualEntityDto payer;
    private EagerLegalEntityDto destination;

    private BankBranchDto bankBranch;
    private String checkingAccount;
    private PaymentPurposeDto purpose;
}
