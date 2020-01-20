package ru.iteco.account.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.iteco.account.domain.Account;
import ru.iteco.account.domain.Operation;
import ru.iteco.account.dto.OperationDto;
import ru.iteco.account.service.AccountService;
import ru.iteco.account.service.OperationService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("account")
public class AccountController {

    private AccountService accountService;
    private OperationService operationService;

    @Autowired
    public AccountController(AccountService accountService, OperationService operationService) {
        this.accountService = accountService;
        this.operationService = operationService;
    }

    /**
     * Method for getting all accounts
     * example url = "/account"
     * @return
     */
    @GetMapping
    public List<Account> getAccounts(){
        return accountService.findAll();
    }

    /**
     * Method for getting account by id
     * example url = "/account/1"
     * @param account
     * @return
     */
    @GetMapping("{id}")
    public Account getAccountById(@PathVariable("id") Account account){
        return account;
    }

    /**
     * Method for creating account
     * example url = "/account"
     * example json = {"balance":"100.00"}
     * @param account
     * @return
     */
    @PostMapping
    public Account create(@RequestBody Account account) {
        account.setCreationDate(LocalDateTime.now());
        Account acc = accountService.save(account);
        if (acc != null && acc.getBalance().compareTo(BigDecimal.ZERO) == 1){
            Operation operation = OperationDto.toDomain(acc);
            operationService.save(operation);
        }
        return acc;
    }

    /**
     * Method for updating account
     * example url = "/account/1"
     * example json = {"balance":"200.00"}
     * @param accountFromDb
     * @param account
     * @return
     */
    @PutMapping("{id}")
    public Account update(
            @PathVariable("id") Account accountFromDb,
            @RequestBody Account account
    ) {
        BeanUtils.copyProperties(account, accountFromDb, "id", "creationDate");
        return accountService.save(accountFromDb);
    }

    /**
     * Method for deleting account
     * example url = "/account/1"
     * @param account
     */
    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Account account) {
        accountService.delete(account);
    }

}
