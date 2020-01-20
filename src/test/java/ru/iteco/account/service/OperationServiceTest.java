package ru.iteco.account.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.iteco.account.domain.Account;
import ru.iteco.account.domain.Operation;
import ru.iteco.account.domain.TypeOperation;
import ru.iteco.account.repository.AccountRepository;
import ru.iteco.account.repository.OperationRepository;

import javax.naming.OperationNotSupportedException;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OperationServiceTest {

    private List<Operation> operations;
    private Operation operation;
    private Account account;

    @Autowired
    private OperationService operationService;

    @MockBean
    private OperationRepository operationRepository;

    @MockBean
    private AccountService accountService;

    @Before
    public void setUp() {
        operation = new Operation();
        operation.setId(1L);
        operation.setAmount(new BigDecimal("100.01"));
        operation.setTypeOperation(TypeOperation.ADD);
        account = new Account(1L, new BigDecimal("100.01"));
        operation.setAccount(account);
        operations = Arrays.asList(operation);
    }

    @Test
    public void findAllById() {
        when(operationRepository.findAllById(any(Long.class))).thenReturn(operations);
        Assert.assertEquals(operationService.findAllById(operation.getId()), operations);
        Mockito.verify(operationRepository, Mockito.times(1)).findAllById(any(Long.class));
    }

    @Test
    public void createOperation() {
        when(operationRepository.save(any(Operation.class))).thenReturn(operation);
        when(accountService.getAccountById(any(Long.class))).thenReturn(account);
        Assert.assertEquals(operationService.createOperation(operation), operation);
        Mockito.verify(operationRepository, Mockito.times(1)).save(any(Operation.class));
    }

    @Test
    public void save() {
        when(operationRepository.save(any(Operation.class))).thenReturn(operation);
        Assert.assertEquals(operationService.save(operation), operation);
        Mockito.verify(operationRepository, Mockito.times(1)).save(any(Operation.class));
    }
}