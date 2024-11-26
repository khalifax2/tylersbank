package com.tylersbank.account.controller;

import com.google.gson.JsonObject;
import com.tylersbank.account.dto.AccountDto;
import com.tylersbank.account.dto.CustomerDto;
import com.tylersbank.account.enums.AccountTypeEnum;
import com.tylersbank.account.enums.GenderEnum;
import com.tylersbank.account.exceptions.AccountAlreadyExistException;
import com.tylersbank.account.service.AccountService;
import com.tylersbank.account.service.client.CustomerFeignClient;
import org.apache.http.client.methods.HttpHead;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AccountControllerIntegrationTest {

    @MockBean
    private CustomerFeignClient customerFeignClient;

    @Autowired
    private TestRestTemplate testRestTemplate;

    private CustomerDto mockCustomer;
    private JSONObject jsonObject;
    private HttpHeaders httpHeaders;

    @BeforeAll
    void setup() throws JSONException {
        httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        jsonObject = new JSONObject();
        jsonObject.put("email", "echo1@gmail.com");
        jsonObject.put("accountType", AccountTypeEnum.SAVINGS);
        jsonObject.put("currency", "PHP");

        mockCustomer = CustomerDto.builder()
                .customerId(UUID.randomUUID().toString())
                .firstName("Jericho")
                .lastName("Castro")
                .email("echo1@gmail.com")
                .mobileNumber("09174646125")
                .gender(GenderEnum.MALE)
                .dateOfBirth(LocalDate.of(1998,8,18))
                .build();
    }


    @Test
    @Order(1)
    void testCreateAccount_whenValidDetailsProvided_returnsNewAccountDetails() throws JSONException {
        // Arrange
        HttpEntity<String> request = new HttpEntity<>(jsonObject.toString(), httpHeaders);
        when(customerFeignClient.findByEmail(any())).thenReturn(Optional.of(mockCustomer));

        // Act
        ResponseEntity<AccountDto> response = testRestTemplate.postForEntity("/account/create", request, AccountDto.class);
        AccountDto createdAccount = response.getBody();

        // Assert
        assertThat(createdAccount).isNotNull();
        assertThat(HttpStatus.CREATED).isEqualTo(response.getStatusCode());
        assertThat("echo1@gmail.com").isEqualTo(createdAccount.getEmail());
    }


    @Test
    @Order(2)
    void testCreateAccount_whenAccountAlreadyExists_returnAccountAlreadyExist() {
        // Arrange
        HttpEntity<String> request = new HttpEntity<>(jsonObject.toString(), httpHeaders);
        when(customerFeignClient.findByEmail(any())).thenReturn(Optional.of(mockCustomer));

        // Act &
        ResponseEntity<AccountDto> response = testRestTemplate.postForEntity("/account/create", request, AccountDto.class);
        AccountDto createdAccount = response.getBody();

        // Assert
        assertThat(HttpStatus.BAD_REQUEST).isEqualTo(response.getStatusCode());
    }


}
