package com.tylersbank.account.controller;

import com.tylersbank.account.dto.AccountDto;
import com.tylersbank.account.service.AccountService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    @PostMapping("/create")
    public ResponseEntity<AccountDto> create(@Valid @RequestBody AccountDto accountDto) {
        AccountDto newAccount = accountService.create(accountDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(newAccount);
    }

    @GetMapping("/getAccount")
    public ResponseEntity<AccountDto> getAccount(@RequestParam String accountNo) {
        AccountDto accountDto = accountService.getAccount(accountNo);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(accountDto);
    }

    @PutMapping("/updateBalance")
    public ResponseEntity<AccountDto> updateBalance(@Valid @RequestBody AccountDto accountDto) {
        AccountDto account = accountService.updateBalance(accountDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(account);
    }

    @Retry(name="test", fallbackMethod = "testFallback")
    @GetMapping("/test")
    public ResponseEntity<String> test() throws TimeoutException {
        logger.debug("Account Service > test called");

        throw new TimeoutException();
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .body("Testing account service");
    }

    public ResponseEntity<String> testFallback(Throwable throwable) {
        logger.debug("Fallback > Account Service > test called");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Fallback Testing account service");
    }

    @RateLimiter(name="test2", fallbackMethod = "test2Fallback")
    @GetMapping("/test2")
    public ResponseEntity<String> test2() throws TimeoutException {
        logger.debug("Account Service > test called");

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Testing2 account service");
    }

    public ResponseEntity<String> test2Fallback(Throwable throwable) {
        logger.debug("Fallback > Account Service > test called");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Fallback2 Testing account service");
    }

}
