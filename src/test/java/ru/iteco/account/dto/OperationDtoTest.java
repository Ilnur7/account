package ru.iteco.account.dto;

import org.junit.Assert;
import org.junit.Test;
import ru.iteco.account.domain.Account;
import ru.iteco.account.domain.Operation;

import java.math.BigDecimal;

public class OperationDtoTest {

    @Test
    public void toDomain() {
        Account account = new Account(1L, new BigDecimal("100.01"));
        Operation operation = OperationDto.toDomain(account);
        Assert.assertEquals(account, operation.getAccount());
    }
}

