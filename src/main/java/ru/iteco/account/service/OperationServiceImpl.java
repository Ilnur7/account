package ru.iteco.account.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.iteco.account.domain.Account;
import ru.iteco.account.domain.Operation;
import ru.iteco.account.domain.TypeOperation;
import ru.iteco.account.exception.NegativeBalanceException;
import ru.iteco.account.exception.NotFoundException;
import ru.iteco.account.repository.OperationRepository;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Transactional(isolation = Isolation.REPEATABLE_READ)
@Service
public class OperationServiceImpl implements OperationService {

    private AccountService accountService;
    private OperationRepository operationRepository;

    @Autowired
    public OperationServiceImpl(AccountService accountService, OperationRepository operationRepository) {
        this.accountService = accountService;
        this.operationRepository = operationRepository;
    }

    @Override
    public List<Operation> findAllById(Long id) {
        return operationRepository.findAllById(id);
    }

    @Override
    public Operation createOperation(Operation operation) {
        Account accountById = accountService.getAccountById(operation.getAccount().getId());
        BigDecimal amount = updateBalance(operation, accountById);
        amount.setScale(2, BigDecimal.ROUND_FLOOR);
        accountById.setBalance(amount);
        operation.setAccount(accountById);
        accountService.save(accountById);
        log.info("Update balance in account");
        Operation operationSave = operationRepository.save(operation);
        log.info("Save operation in database");
        return operationSave;
    }

    private BigDecimal updateBalance(Operation operation, Account account) {
        BigDecimal amount = null;
        if (operation.getTypeOperation().equals(TypeOperation.SUBTRACT)) {
            amount = account.getBalance().subtract(operation.getAmount());
            if (amount.compareTo(BigDecimal.ZERO) == -1) {
                log.warn("Resulting value has a negative number");
                throw new NegativeBalanceException();
            }
        } else if (operation.getTypeOperation().equals(TypeOperation.ADD)) {
            amount = account.getBalance().add(operation.getAmount());
        } else {
            log.warn("Type of operation is not defined");
            new NotFoundException();
        }
        return amount;
    }

    @Override
    public Operation save(Operation operation) {
        Operation op = operationRepository.save(operation);
        log.info("Save account: " + op.toString() + " in database");
        return op;
    }
}
