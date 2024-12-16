package com.caju.authorizer.controller;

import com.caju.authorizer.model.Transaction;
import com.caju.authorizer.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public ResponseEntity<String> processTransaction(@RequestBody Transaction transaction) {
        String response = transactionService.processTransaction(transaction);
        return ResponseEntity.ok(response);
    }
}
