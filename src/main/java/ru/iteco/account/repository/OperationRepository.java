package ru.iteco.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.iteco.account.domain.Operation;

import java.util.List;

@Repository
public interface OperationRepository extends JpaRepository<Operation, Long> {
    /**
     * Method for finding all operations by id
     * @param id
     * @return
     */
    List<Operation> findAllById(Long id);
}

