package ru.iteco.account.service;

import ru.iteco.account.domain.Account;

import java.util.List;

public interface AccountService {
    /**
     * Method for getting account by id
     * @param id
     * @return
     */
    Account getAccountById(Long id);

    /**
     * Method for saving account in database
     * @param account
     * @return
     */
    Account save(Account account);

    /**
     * Method for finding all accounts
     * @return
     */
    List<Account> findAll();

    /**
     * Method for deleting account
     * @param account
     */
    void delete(Account account);
}
