package ru.iteco.account.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.iteco.account.domain.Account;
import ru.iteco.account.repository.AccountRepository;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountServiceTest {

    private Optional<Account> accountOptional;
    private Account account;
    private List<Account> accounts;

    @Autowired
    private AccountService accountService;

    @MockBean
    private AccountRepository accountRepository;

    @Before
    public void setUp() {
        account = new Account(1L, new BigDecimal("100"));
        accounts = Arrays.asList(account);
        accountOptional = Optional.of(account);
    }

    @Test
    public void getAccountById() {
        when(accountRepository.findById(any(Long.class))).thenReturn(accountOptional);
        Assert.assertEquals(accountService.getAccountById(account.getId()), account);
        Mockito.verify(accountRepository, Mockito.times(1)).findById(any(Long.class));
    }

    @Test
    public void save() {
        when(accountRepository.save(any(Account.class))).thenReturn(account);
        Assert.assertEquals(accountService.save(account), account);
        Mockito.verify(accountRepository, Mockito.times(1)).save(any(Account.class));
    }

    @Test
    public void findAll() {
        when(accountRepository.findAll()).thenReturn(accounts);
        Assert.assertEquals(accountService.findAll(), accounts);
        Mockito.verify(accountRepository, Mockito.times(1)).findAll();
    }
}