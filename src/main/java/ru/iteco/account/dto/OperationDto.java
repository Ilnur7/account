package ru.iteco.account.dto;

import ru.iteco.account.domain.Account;
import ru.iteco.account.domain.Operation;
import ru.iteco.account.domain.TypeOperation;

import java.time.LocalDateTime;

public class OperationDto {
    public static Operation toDomain(Account account) {
        Operation operation = new Operation();
        operation.setAccount(account);
        operation.setCreationDate(LocalDateTime.now());
        operation.setAmount(account.getBalance());
        operation.setTypeOperation(TypeOperation.ADD);
        return operation;
    }
}
