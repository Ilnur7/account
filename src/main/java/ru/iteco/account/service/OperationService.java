package ru.iteco.account.service;

import ru.iteco.account.domain.Operation;

import java.util.List;

public interface OperationService {

    /**Method for finding all operations by id
     * @param id
     * @return
     */
    List<Operation> findAllById(Long id);

    /**
     * Method for updating balance in account
     * @param operation
     * @return
     */
    Operation createOperation(Operation operation);

    /**
     * Method for saving operation  in database
     * @param operation
     * @return
     */
    Operation save(Operation operation);
}
