package ru.iteco.account.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.iteco.account.domain.Account;
import ru.iteco.account.domain.Operation;
import ru.iteco.account.service.AccountService;
import ru.iteco.account.service.OperationService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/operation")
public class OperationController {

    private OperationService operationService;

    @Autowired
    public OperationController(OperationService operationService) {
        this.operationService = operationService;
    }

    /**
     * Method for getting operations by id
     * example url = "/operation/1"
     * @param account
     * @return
     */
    @GetMapping("{id}")
    public List<Operation> getListById(@PathVariable("id") Account account){
        return operationService.findAllById(account.getId());
    }

    /**
     * Method for creating operation
     * example url = "/operation"
     * example json = {"account":{"id":"2"},"amount":"100.00","typeOperation":"add"}
     * @param operation
     * @return
     */
    @PostMapping
    public Operation createOperation(@RequestBody Operation operation){
        operation.setCreationDate(LocalDateTime.now());
        return operationService.createOperation(operation);
    }
}
