package ru.iteco.account.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.iteco.account.domain.Account;
import ru.iteco.account.repository.AccountRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Transactional(isolation = Isolation.REPEATABLE_READ)
@Service
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }


    @Override
    public Account getAccountById(Long id) {
        Optional<Account> account = accountRepository.findById(id);
        if (account.isPresent()) {
            return account.get();
        } else {
            throw new RuntimeException("unfounded account");
        }
    }

    @Override
    public Account save(Account account) {
        Account acc = accountRepository.save(account);
        log.info("Save account: " + acc.toString() + " in database");
        return acc;
    }

    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public void delete(Account account) {
        accountRepository.delete(account);
        log.info("Delete account: " + account.toString() + " from database");
    }
}
