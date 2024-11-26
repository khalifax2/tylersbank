package com.tylersbank.transaction.controller;

import com.tylersbank.transaction.dto.TransactionDetailsDto;
import com.tylersbank.transaction.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/withdrawal")
    public ResponseEntity<String> withdrawal(@RequestBody TransactionDetailsDto transactionDetailsDto) {
        transactionService.withdrawal(transactionDetailsDto);
        return ResponseEntity.status(HttpStatus.OK).body("Transaction Success.");
    }

    @PostMapping("/deposit")
    public ResponseEntity<String> deposit(@RequestBody TransactionDetailsDto transactionDetailsDto) {
        transactionService.deposit(transactionDetailsDto);
        return ResponseEntity.status(HttpStatus.OK).body("Transaction Success.");
    }

    @GetMapping("/getAllTransactions")
    public ResponseEntity<List<TransactionDetailsDto>> getAllTransactions() {
        List<TransactionDetailsDto> transactionDetailsDtoList = transactionService.getAllTransactions();
        return ResponseEntity.status(HttpStatus.OK).body(transactionDetailsDtoList);
    }

    @GetMapping("/getTransaction")
    public ResponseEntity<List<TransactionDetailsDto>> getTransaction(@RequestParam String accountNo) {
        List<TransactionDetailsDto> transactionDetailsDtoList = transactionService.getTransaction(accountNo);
        return ResponseEntity.status(HttpStatus.OK).body(transactionDetailsDtoList);
    }
}
