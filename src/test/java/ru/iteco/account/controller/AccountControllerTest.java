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

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-account-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class AccountControllerTest {

    private String accountsStr;
    private List<Account> accounts;
    private String accountStr;
    private Account account;

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setUp() throws JsonProcessingException {
        account = new Account(1L, new BigDecimal("100.01"));
        accounts = Arrays.asList(account);

        accountStr = new ObjectMapper().writeValueAsString(account);
        accountsStr = new ObjectMapper().writeValueAsString(accounts);
    }

    @Sql(value = {"/create-account-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Test
    public void getAccounts() throws Exception {
        this.mockMvc.perform(get("/account"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string(containsString(accountsStr)));
    }

    @Sql(value = {"/create-account-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Test
    public void getAccountById() throws Exception {
        this.mockMvc.perform(get("/account/1"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string(containsString(accountStr)));
    }

    @Test
    public void create() throws Exception {
        this.mockMvc.perform(post("/account")
                .content(accountStr)
                .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isNotEmpty())
            .andExpect(jsonPath("$.id").value(account.getId()))
            .andExpect(jsonPath("$.balance").value(account.getBalance()));
    }

    @Sql(value = {"/create-account-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Test
    public void update() throws Exception {
        account.setBalance(new BigDecimal("500.05"));
        accountStr = new ObjectMapper().writeValueAsString(account);

        this.mockMvc.perform(put("/account/1")
                .content(accountStr)
                .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isNotEmpty())
            .andExpect(jsonPath("$.balance").value(account.getBalance()));
    }
}