package com.tylersbank.account.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tylersbank.account.dto.AccountDto;
import com.tylersbank.account.enums.AccountStatusEnum;
import com.tylersbank.account.enums.AccountTypeEnum;
import com.tylersbank.account.service.AccountService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import java.util.Random;
import java.util.UUID;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = AccountController.class)
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @Autowired
    private ObjectMapper objectMapper;

    private AccountDto requestAccountDto;

    @BeforeEach
    void setAccountRequest() {
        requestAccountDto = AccountDto.builder()
                .email("echo1@gmail.com")
                .accountType(AccountTypeEnum.SAVINGS)
                .currency("PHP").build();
    }

    private AccountDto getSampleAccountDto() {
        return AccountDto.builder()
                .accountId(UUID.randomUUID().toString())
                .accountNo("001")
                .email("echo1@gmail.com")
                .accountType(AccountTypeEnum.SAVINGS)
                .currency("PHP")
                .status(AccountStatusEnum.ACTIVE)
                .build();
    }

    @Test
    void testCreateAccount_whenValidDetailsProvided_returnsNewAccountDetails() throws Exception {
        // Arrange
        when(accountService.create(any(AccountDto.class))).thenReturn(getSampleAccountDto());

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/account/create")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestAccountDto));

        // Act
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        String responseBodyAsString = mvcResult.getResponse().getContentAsString();
        AccountDto createdAccount = objectMapper.readValue(responseBodyAsString, AccountDto.class);


        // Assert
        Assertions.assertAll(
                () -> Assertions.assertNotNull(createdAccount, "Created account should not be null"),
                () -> Assertions.assertEquals(requestAccountDto.getEmail(), createdAccount.getEmail(), "Returned email was incorrect"),
                () -> Assertions.assertEquals(requestAccountDto.getAccountType(), createdAccount.getAccountType(), "Returned account type was incorrect"),
                () -> Assertions.assertEquals(requestAccountDto.getCurrency(), createdAccount.getCurrency(), "Returned currency was incorrect"),
                () -> Assertions.assertEquals(AccountStatusEnum.ACTIVE, createdAccount.getStatus() , "Returned account status was incorrect"),
                () -> Assertions.assertNotNull(createdAccount.getAccountId(), "Returned accountId was null"),
                () -> Assertions.assertNotNull(createdAccount.getAccountNo(), "Returned accountNo was null"),
                () -> Assertions.assertFalse(createdAccount.getAccountId().isEmpty(), "Returned accountId was empty"),
                () -> Assertions.assertFalse(createdAccount.getAccountNo().isEmpty(), "Returned accountNo was empty"));
    }

    @Test
    void testGetAccount_whenValidAccountNoProvided_returnAccountDetails() throws Exception {
        // Arrange
        AccountDto savedAccount = getSampleAccountDto();
        when(accountService.getAccount(anyString())).thenReturn(savedAccount);

        // Act
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .get("/account/getAccount")
                .param("accountNo", "001")
                .accept(MediaType.APPLICATION_JSON)).andReturn();
        String responseBodyAsString = mvcResult.getResponse().getContentAsString();
        AccountDto savedAccountResponse = objectMapper.readValue(responseBodyAsString, AccountDto.class);

        // Assert
        Assertions.assertAll(
                () -> Assertions.assertEquals(savedAccount.getAccountId(), savedAccountResponse.getAccountId()),
                () -> Assertions.assertEquals(savedAccount.getAccountNo(), savedAccountResponse.getAccountNo()),
                () -> Assertions.assertEquals(savedAccount.getEmail(), savedAccountResponse.getEmail()),
                () -> Assertions.assertEquals(savedAccount.getAccountType(), savedAccountResponse.getAccountType()),
                () -> Assertions.assertEquals(savedAccount.getCurrency(), savedAccountResponse.getCurrency()),
                () -> Assertions.assertEquals(savedAccount.getStatus(), savedAccountResponse.getStatus())
        );
    }

    private RequestBuilder createAccountRequestBuilder() throws JsonProcessingException {
        return MockMvcRequestBuilders
                .post("/account/create")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestAccountDto));
    }


    @Test
    void testCreateUser_whenInvalidEmail_returnsBadRequest() throws Exception {
        // Arrange
        requestAccountDto.setEmail("invalid-email");

        // Act & Assert
        mockMvc.perform(createAccountRequestBuilder())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.email").value("Enter a valid email"));
    }

    @Test
    void testCreateUser_whenEmailIsBlank_returnsBadRequest() throws Exception {
        // Arrange
        requestAccountDto.setEmail("");

        // Act & Assert
        mockMvc.perform(createAccountRequestBuilder())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.email").value("Email must not be blank"));
    }

}