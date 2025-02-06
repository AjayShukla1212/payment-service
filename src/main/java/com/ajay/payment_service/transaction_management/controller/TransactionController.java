package com.ajay.payment_service.transaction_management.controller;

import com.ajay.payment_service.transaction_management.dto.TransactionDTO;
import com.ajay.payment_service.transaction_management.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/initiate")
    public ResponseEntity<String> initiateTransaction(@Valid @RequestBody final TransactionDTO transaction) {
        String response = transactionService.initiateTransaction(transaction);
        return ResponseEntity.ok(response);
    }
}
