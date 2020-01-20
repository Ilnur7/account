package ru.iteco.account.repository;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import ru.iteco.account.domain.Account;
import ru.iteco.account.domain.Operation;
import ru.iteco.account.domain.TypeOperation;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Sql(value = {"/delete-account.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Sql(value = {"/create-account.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@RunWith(SpringRunner.class)
@SpringBootTest
public class OperationRepositoryTest {

    private Operation operation;
    private List<Operation> operations;

    @Autowired
    OperationRepository operationRepository;

    @Before
    public void setUp() {
        operation = new Operation();
        operation.setId(1L);
        operation.setAmount(new BigDecimal("100.01"));
        operation.setTypeOperation(TypeOperation.ADD);
        operation.setAccount(new Account(1L, new BigDecimal("100.01")));
        operations = Arrays.asList(operation);
    }

    @Test
    public void findAllById() {
        Assert.assertEquals(operationRepository.findAllById(operation.getId()), operations);
    }
}