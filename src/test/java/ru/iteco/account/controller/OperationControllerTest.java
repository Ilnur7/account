package ru.iteco.account.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.iteco.account.domain.Account;
import ru.iteco.account.domain.Operation;
import ru.iteco.account.domain.TypeOperation;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/delete-account.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Sql(value = {"/create-account.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class OperationControllerTest {

    private String operationStr;
    private Operation operation;

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setUp() throws JsonProcessingException {
        operation = new Operation();
        operation.setId(1L);
        operation.setAmount(new BigDecimal("100.01"));
        operation.setTypeOperation(TypeOperation.ADD);
        operation.setAccount(new Account(1L, new BigDecimal("100.01")));
        operationStr = new ObjectMapper().writeValueAsString(operation);
    }


    @Test
    public void getListById() throws Exception {
        this.mockMvc.perform(get("/operation/1"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isNotEmpty())
            .andExpect(jsonPath("$[0].id").value(operation.getId()))
            .andExpect(jsonPath("$[0].amount").value(operation.getAmount()));
    }

    @Test
    public void createOperation() throws Exception {
        this.mockMvc.perform(post("/operation")
                .content(operationStr)
                .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(operation.getId()))
            .andExpect(jsonPath("$.amount").value(operation.getAmount()));
    }
}